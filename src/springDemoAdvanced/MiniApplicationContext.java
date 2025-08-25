package springDemoAdvanced;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
@interface Autowired {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface PostConstruct {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface PreDestroy {}

public class MiniApplicationContext implements AutoCloseable {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Set<Class<?>> creating = new HashSet<>();

    public MiniApplicationContext(String basePackage) {
        try {
            Set<Class<?>> comps = scanPackage(basePackage);

            for (Class<?> c :comps) {
                createBean(c);
            }

            for (Object bean : beans.values()) {
                injectFields(bean);
                injectSetters(bean);
            }

            for (Object bean : beans.values()) {
                callAnnotated(bean, PostConstruct.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Faield to initialize context", e);
        }
    }

    private void callAnnotated(Object bean, Class<? extends Annotation> ann) throws Exception {
        for (Method m : bean.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(ann) && m.getParameterCount() == 0) {
                m.setAccessible(true);
                m.invoke(bean);
            }
        }
    }

    private void injectFields(Object bean) throws Exception {
        for (Field f : bean.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Autowired.class)) {
                Object dep = createBean(f.getType());
                f.setAccessible(true);
                f.set(bean, dep);
            }
        }
    }

    private void injectSetters(Object bean) throws Exception {
        for (Method m : bean.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Autowired.class) && m.getParameterCount() == 1) {
                Class<?> depType = m.getParameterTypes()[0];
                Object dep = createBean(depType);
                m.setAccessible(true);
                m.invoke(bean, dep);
            }
        }
    }

    private Object createBean(Class<?> type) throws Exception {
        if (beans.containsKey(type)) return beans.get(type);
        if (!isComponent(type)) return null;

        if (!creating.add(type)) {
            throw new IllegalStateException("Circular dependency detected: " + type.getName());
        }

        Constructor<?> ctor = chooseConstructor(type);
        Object[] args = Arrays.stream(ctor.getParameterTypes())
                .map(p -> {
                    try { return createBean(p); }
                    catch (Exception e) { throw new RuntimeException(e); }
                })
                .toArray();

        Object instance = ctor.newInstance(args);
        beans.put(type, instance);
        creating.remove(type);
        return instance;
    }

    private Constructor<?> chooseConstructor(Class<?> type) throws NoSuchMethodException {
        Constructor<?>[] publicCtors = type.getConstructors();
        Optional<Constructor<?>> autowired = Arrays.stream(publicCtors)
                .filter(ct -> ct.isAnnotationPresent(Autowired.class))
                .findFirst();
        if (autowired.isPresent()) return autowired.get();

        try {
            return type.getDeclaredConstructor();
        } catch (NoSuchMethodException ignored) {}

        if (publicCtors.length == 0) {
            throw new NoSuchMethodException("No accessible constructor for " + type.getName());
        }
        return publicCtors[0];
    }

    private Set<Class<?>> scanPackage(String basePackage)
            throws IOException, URISyntaxException, ClassNotFoundException {
        String path = basePackage.replace('.', '/');
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = cl.getResources(path);

        Set<Class<?>> result = new HashSet<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            File root = new File(url.toURI());
            if (root.isDirectory()) {
                scanDirRecursive(basePackage, root, root, result);
            }
        }
        return result;
    }

    private void scanDirRecursive(String basePackage, File root, File dir, Set<Class<?>> out)
        throws ClassNotFoundException {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            if (f.isDirectory()) {
                scanDirRecursive(basePackage, root, f, out);
            } else if (f.getName().endsWith(".class")) {
                String className = toClassName(basePackage, root, f);
                Class<?> clazz = Class.forName(className);
                if (isComponent(clazz)) out.add(clazz);
            }
        }
    }

    private String toClassName(String basePackage, File root, File classFile) {
        String absRoot = root.getAbsolutePath();
        String absFile = classFile.getAbsolutePath();
        String rel = absFile.substring(absRoot.length() + 1, absFile.length() - ".class".length());
        String relDots = rel.replace(File.separatorChar, '.');
        return basePackage + (relDots.isEmpty() ? "" : "." + relDots);
    }

    private boolean isComponent(Class<?> c) {
        return c.isAnnotationPresent(Component.class);
    }

    public <T> void run(Class<T> rootClass) {
        try {
            createBean(rootClass);
            Object root = beans.get(rootClass);
            if (root == null) {
                throw new IllegalStateException("Root bean not found: " + rootClass.getName());
            }
            Method run = rootClass.getMethod("run");
            run.invoke(root);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Root bean " + rootClass.getName() + " must have public void run()", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run root bean " + rootClass.getName(), e);
        }
    }

    @Override
    public void close() throws Exception {
        for (Object bean : beans.values()) {
            try { callAnnotated(bean, PreDestroy.class); }
            catch (Exception e) {e.printStackTrace(); }
        }

    }
}

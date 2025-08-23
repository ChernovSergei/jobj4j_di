package springDemoAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
@interface Autowired {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MainApp {}

public class SimpleContext {
    private Map<Class<?>, Object> beans = new HashMap<>();

    public void register(Class<?> clazz) {
        try {
            if (!clazz.isAnnotationPresent(Component.class) &&
            !clazz.isAnnotationPresent(MainApp.class)) {
                return;
            }

            if (beans.containsKey(clazz)) return;

            Constructor<?> constructor = findConstructor(clazz);

            Object[] params = Arrays.stream(constructor.getParameterTypes())
                    .map(dep -> {
                        if (!beans.containsKey(dep)) {
                            register(dep);
                        }
                        return beans.get(dep);
                    })
                    .toArray();
            Object instance = constructor.newInstance(params);
            beans.put(clazz, instance);
        } catch (Exception e) {
            throw new RuntimeException("ERROR! Class creation error: " + clazz, e);
        }
    }

    private Constructor<?> findConstructor (Class<?> clazz) {
        for (Constructor<?> c : clazz.getConstructors()) {
            if (c.isAnnotationPresent(Autowired.class)) {
                return c;
            }
        }
        return clazz.getConstructors()[0];
    }

    public void runMainApp() {
        beans.keySet().stream()
                .filter(c -> c.isAnnotationPresent(MainApp.class))
                .findFirst()
                .ifPresent(mainClass -> {
                    try {
                        Method run = mainClass.getMethod("run");
                        run.invoke(beans.get(mainClass));
                    } catch (Exception e) {
                        throw new RuntimeException("ERROR! @MainApp launch error", e);
                    }
                });
    }
}

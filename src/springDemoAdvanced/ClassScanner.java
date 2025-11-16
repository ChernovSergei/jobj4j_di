package springDemoAdvanced;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {
    static Set<Class<?>> scanPackage(String basePackage)
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

    private static void scanDirRecursive(String basePackage, File root, File dir, Set<Class<?>> out)
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

    private static String toClassName(String basePackage, File root, File classFile) {
        String absRoot = root.getAbsolutePath();
        String absFile = classFile.getAbsolutePath();
        String rel = absFile.substring(absRoot.length() + 1, absFile.length() - ".class".length());
        String relDots = rel.replace(File.separatorChar, '.');
        return basePackage + (relDots.isEmpty() ? "" : "." + relDots);
    }

    static boolean isComponent(Class<?> c) {
        return c.isAnnotationPresent(Component.class);
    }
}

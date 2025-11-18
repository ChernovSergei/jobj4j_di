package springDemoReflection;

import java.util.Map;
import java.util.HashMap;

public class MiniReflection {
    // Реестр "метод по имени" для конкретного класса
    private static final Map<Class<?>, Map<String, MethodLike>> registry = new HashMap<>();

    static {
        // Регистрируем "метод" run для класса Worker
        Map<String, MethodLike> methods = new HashMap<>();
        methods.put("run", new MethodLike("run", target -> ((Worker) target).run()));
        registry.put(Worker.class, methods);
    }

    // "Поиск" метода по имени, как getMethod("run")
    public static MethodLike getMethod(Class<?> clazz, String name) {
        Map<String, MethodLike> methods = registry.get(clazz);
        if (methods == null || !methods.containsKey(name)) {
            throw new RuntimeException("Метод не найден: " + clazz.getName() + "." + name + "()");
        }
        return methods.get(name);
    }

}

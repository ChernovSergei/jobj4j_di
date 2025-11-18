package springDemoReflection;

public class Launcher {
    public static void main(String[] args) {
        Class<?> clazz = Worker.class;
        Object instance = new Worker();

        // Аналог: Method method = clazz.getMethod("run");
        MethodLike method = MiniReflection.getMethod(clazz, "run");

        // Аналог: method.invoke(instance);
        method.invoke(instance);
    }
}

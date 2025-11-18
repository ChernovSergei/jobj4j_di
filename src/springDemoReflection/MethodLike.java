package springDemoReflection;

import java.util.function.Consumer;

public class MethodLike {
    private final String name;
    private final Consumer<Object> invoker; // как вызвать метод без аргументов

    public MethodLike(String name, Consumer<Object> invoker) {
        this.name = name;
        this.invoker = invoker;
    }

    public String getName() {
        return name;
    }

    public void invoke(Object target) {
        // тут могли бы быть проверки доступа, типов и т.д.
        invoker.accept(target);
    }
}

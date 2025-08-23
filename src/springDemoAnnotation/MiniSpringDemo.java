package springDemoAnnotation;

public class MiniSpringDemo {
    public static void main(String[] args) {
        SimpleContext context = new SimpleContext();
        context.register(Car.class);
        context.register(Engine.class);
        context.register(Application.class);

        context.runMainApp();
    }
}

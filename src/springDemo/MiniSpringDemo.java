package springDemo;

public class MiniSpringDemo {
    public static void main(String[] args) {
        SimpleContext context = new SimpleContext();
        context.registerBean(Car.class);
        context.registerBean(Engine.class);

        Car car = context.getBean(Car.class);
        car.drive();
    }
}

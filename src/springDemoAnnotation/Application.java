package springDemoAnnotation;

@MainApp
public class Application {
    private final Car car;

    @Autowired
    public Application(Car car) {
        this.car = car;
    }

    public void run() {
        car.drive();
    }
}

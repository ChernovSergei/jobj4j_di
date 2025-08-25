package springDemoAdvanced;

@Component
public class AppRunner {
    private final Car car;

    @Autowired
    public AppRunner(Car car) {
        this.car = car;
    }

    public void run() {
        car.drive();
    }
}

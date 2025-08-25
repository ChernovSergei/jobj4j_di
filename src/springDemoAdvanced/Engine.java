package springDemoAdvanced;

@Component
public class Engine {
    @PostConstruct
    public void init() {
        System.out.println("Engine: init");
    }

    public void start() {
        System.out.println("Engine: start");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Engine: shutdown");
    }
}

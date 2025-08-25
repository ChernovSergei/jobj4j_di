package springDemoAdvanced;

public class Launcher {
    public static void main(String[] args) throws Exception {
        try (MiniApplicationContext ctx = new MiniApplicationContext("springDemoAdvanced")) {
            ctx.run(AppRunner.class);
        }
    }
}

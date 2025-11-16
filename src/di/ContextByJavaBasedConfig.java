package di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import di.model.StartUI;

public class ContextByJavaBasedConfig {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("di.model");
        var ui = context.getBean(StartUI.class);
        ui.add("Afanasy Afanasievich");
        ui.add("Aleksandr Aleksandrovich");
        ui.print();
    }
}

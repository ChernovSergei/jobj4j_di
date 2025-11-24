import annotation3.Man;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAnnotation3 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("annotation3");
        Man man = context.getBean(Man.class);
        man.print();
    }
}

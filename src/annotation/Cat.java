package annotation;

import org.springframework.stereotype.Component;

@Component
public class Cat implements Pet {
    private String name = "Cat";

    @Override
    public String print() {
        return "Cat name = %s".formatted(name);
    }
}

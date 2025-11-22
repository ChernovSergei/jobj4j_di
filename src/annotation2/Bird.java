package annotation2;

import org.springframework.stereotype.Component;

@Component
public class Bird implements Pet {
    private String name = "Bird";

    @Override
    public String print() {
        return "Bird name = %s".formatted(name);
    }
}

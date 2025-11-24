package annotation3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bird implements Pet {
    @Value("${bird.name:simpleBird}")
    private String name;

    @Override
    public String print() {
        return "Bird name = %s".formatted(name);
    }
}

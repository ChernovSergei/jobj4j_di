package annotation2;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Man {
    @Autowired
    private Dog dog;
    private Cat cat;
    private Bird bird;

    @Autowired
    @Qualifier("Markiza")
    private Cat cat2;

    @Autowired
    @Resource(name = "ResourceCat")
    private Cat cat3;

    @Autowired
    public Man(Cat cat) {
        this.cat = cat;
    }

    @Autowired
    public void setBird(Bird bird) {
        this.bird = bird;
    }

    public void print() {
        System.out.printf("My Dog: %s%n", dog.print());
        System.out.printf("My favourite Cat: %s%n", cat2.print());
        System.out.printf("My primary Cat: %s%n", cat.print());
        System.out.printf("My resource Cat: %s%n", cat3.print());
        System.out.printf("My Bird: %s%n", bird.print());
    }
}

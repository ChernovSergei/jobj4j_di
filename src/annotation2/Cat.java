package annotation2;

import org.springframework.stereotype.Component;

public class Cat implements Pet {
    private String name = "Cat";
    public Cat() {}

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return "Cat name = %s".formatted(name);
    }
}
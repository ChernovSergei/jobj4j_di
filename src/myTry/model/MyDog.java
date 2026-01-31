package myTry.model;

import org.springframework.stereotype.Component;

@Component
public class MyDog {
    private String name;
    
    public MyDog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

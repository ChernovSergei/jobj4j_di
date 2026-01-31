package myTry.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Man {
    @Autowired
    private List<MyDog> myDogs;

    public void print() {
        myDogs.forEach(d -> System.out.println(d.getName()));
    }

    /*@Autowired
    private Dog dog;

    public void print() {
        System.out.println(dog.getName());
    }*/
}

package annotation3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class Man {
    @Autowired
    private Dog dog;
    private Cat cat;
    private Bird bird;

    @Autowired
    public Man(Cat cat) {
        this.cat = cat;
    }

    @Autowired
    public void setBird(Bird bird) {
        this.bird = bird;
    }

    @Autowired
    private Set<Pet> pets;

    @Autowired
    private List<Dog> dogs;

    @Autowired
    private Map<String, Dog> mapDogs;

    public void print() {
        System.out.println("pets:");
        pets.forEach(s -> System.out.println(s.print()));
        System.out.println("dogs:");
        dogs.forEach(s -> System.out.println(s.print()));
        System.out.println("mapDogs:");
        mapDogs.forEach((key, value) -> System.out.printf("%s -%s%n", key, value.print()));
    }
}

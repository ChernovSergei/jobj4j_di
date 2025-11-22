package annotation2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = "annotation2")
public class Config {

    @Bean
    @Primary
    public Dog getAlfaDog() {
        return new Dog("AlfaDog");
    }

    @Bean(name = "SecondDog")
    public Dog getDog() {
        return new Dog("Second Dog");
    }

    @Bean(name = "Marusya")
    @Primary
    public Cat getCatMarusya() {
        return new Cat("Marusya Cat");
    }

    @Bean(name = "Markiza")
    public Cat getCatMarkiza() {
        return new Cat("Markiza cat");
    }

    @Bean
    public Cat getAnyCat() {
        return new Cat("Any cat");
    }

    @Bean(name = "ResourceCat")
    public Cat getResourceCat() {
        return new Cat("Recource cat");
    }

}

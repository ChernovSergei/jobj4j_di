package myTry;

import myTry.model.MyDog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("myTry")
@PropertySource("classpath:/resources/myTry.properties")
public class Config {
    @Bean
    public MyDog getFirstDog(@Value("${dog.first.name}") String name) {
        return new MyDog(name);
    }

    @Bean
    public MyDog getSecondDog(@Value("${dog.second.name:secondDogName}") String name) {
        return new MyDog(name);
    }
}

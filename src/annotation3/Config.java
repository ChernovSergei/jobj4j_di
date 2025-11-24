package annotation3;

import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan(basePackages = "annotation3")
@PropertySource("classpath:/resources/application.properties")
public class Config {

    @Order(15)
    @Bean
    @Primary
    public Dog getAlfaDog() {
        return new Dog("AlfaDog");
    }

    @Order(14)
    @Bean(name = "SecondDog")
    public Dog getDog() {
        return new Dog("Second Dog");
    }

    @Order(10)
    @Bean(name = "ResourceDog")
    public Dog getResourceDog() {
        return new Dog("Recource dog");
    }

}

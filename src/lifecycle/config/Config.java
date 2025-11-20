package lifecycle.config;

import lifecycle.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean(name = "myProduct",
            initMethod = "initMethod",
            destroyMethod = "destroyMethod"
    )
    public Product getProduct() {
        return new Product("Good Product", 2.5);
    }
}

package nl.vorstdev.example.resource.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
@Configuration
public class ResourceConfiguration {

    @Bean
    public PersonInitializer personInitializer() {
        return new PersonInitializerImpl();
    }
}

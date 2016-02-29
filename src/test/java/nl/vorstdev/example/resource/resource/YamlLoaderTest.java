package nl.vorstdev.example.resource.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ernstvorsteveld on 29/02/16.
 */
public class YamlLoaderTest {

    private String id;

    @Before
    public void initialize() throws IOException {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource<?> load = loader.load("application.yml", new FileSystemResource
                ("src/main/resources/application.yml"), null);
        id = (String) load.getProperty("endpoints.info.id");
    }

    @Test
    public void should_have_id() {
        assertThat(id, is("info"));
    }
}

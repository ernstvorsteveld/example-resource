package nl.vorstdev.example.resource.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ernstvorsteveld on 29/02/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppContextTest.class, ApplicationProperties.class},
        initializers = ConfigFileApplicationContextInitializer.class)
public class YamlFileUserITest {

    @Value("${endpoints.info.id}")
    private String id;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void should_have_id() {
        assertThat(id, is("info"));
        assertThat(applicationProperties.getId(), is("info"));
    }
}

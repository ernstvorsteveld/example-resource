package nl.vorstdev.example.resource.resource;

import nl.vorstdev.example.resource.resource.PersonInitializer;
import nl.vorstdev.example.resource.resource.PersonInitializerImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by ernstvorsteveld on 15/02/16.
 */
public class PersonInitializerImplTest {

    private PersonInitializer personInitializer = new PersonInitializerImpl();

    @Before
    public void init() {
        personInitializer.initialize();
    }

    @Test
    public void should_have_correct_number_of_objects_by_init() {
        assertThat(personInitializer.getPersons().size(), is(10));
    }

    @Test
    public void should_get_a_page() {
        assertThat(personInitializer.getPersons().subList(0, 4).size(), is(4));
    }

}
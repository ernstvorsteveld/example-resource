package nl.vorstdev.example.resource.resource;

import nl.vorstdev.example.resource.domain.Person;

import java.util.List;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
public interface PersonInitializer {

    List<Person> getPersons();

    void initialize();
}

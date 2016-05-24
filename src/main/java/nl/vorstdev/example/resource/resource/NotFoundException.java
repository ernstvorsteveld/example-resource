package nl.vorstdev.example.resource.resource;

import nl.vorstdev.example.resource.domain.Person;

/**
 * Created by ernstvorsteveld on 01/05/16.
 */
public class NotFoundException extends RuntimeException {

    private final String userName;
    private final Class clazz;

    public NotFoundException(String userName, Class<Person> personClass) {
        this.userName = userName;
        this.clazz = personClass;
    }
}

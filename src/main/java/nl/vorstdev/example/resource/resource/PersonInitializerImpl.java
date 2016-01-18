package nl.vorstdev.example.resource.resource;

import com.google.common.collect.Lists;
import nl.vorstdev.example.resource.domain.Person;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
public class PersonInitializerImpl implements PersonInitializer {

    private List<Person> personList;

    @Override
    public List<Person> getPersons() {
        return this.personList;
    }

    @Override
    @PostConstruct
    public void initialize() {
        personList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            personList.add(
                    new Person.PersonBuilder("username" + i)
                            .name("first" + i, "middle" + i, "last" + i)
                            .build());
        }

    }
}

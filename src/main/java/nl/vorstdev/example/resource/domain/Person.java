package nl.vorstdev.example.resource.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;
import java.util.UUID;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
//@ApiModel(description = "The Person object.")
public class Person extends ResourceSupport {

    public static final String USERNAME = "username";
    public static final String FIRSTNAME = "firstname";
    public static final String MIDDLENAME = "middlename";
    public static final String LASTNAME = "lastname";

    private String personId;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;

    public Person() {
        init();
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void init() {
        this.personId = UUID.randomUUID().toString();
    }

    public static class PersonBuilder {

        private String userName;
        private String firstName;
        private String middleName;
        private String lastName;

        public PersonBuilder(String userName) {
            this.userName = userName;
        }

        public PersonBuilder(Map<String, String> personMap) {
            this(personMap.get(USERNAME));
            firstName = personMap.get(FIRSTNAME);
            middleName = personMap.get(MIDDLENAME);
            lastName = personMap.get(LASTNAME);
        }

        public PersonBuilder name(String firstName, String middleName, String lastName) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.setUserName(userName);
            person.setFirstName(firstName);
            person.setMiddleName(middleName);
            person.setLastName(lastName);
            return person;
        }
    }
}

package nl.vorstdev.example.resource.resource;

import nl.vorstdev.example.resource.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
@Controller
@RequestMapping(value = "persons",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonInitializer personInitializer;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Person> personList(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "1") int size) {
        logger.debug("About to get all persons.");
        return personInitializer.getPersons();
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.GET)
    @ResponseBody
    public Person person(@PathVariable("userName") final String userName, HttpServletResponse response) {
        logger.debug("About to load by userName {}.", userName);
        for (Person person : personInitializer.getPersons()) {
            if (person.getUserName().equals(userName)) {
                response.setStatus(HttpServletResponse.SC_OK);
                return person;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Person create(@RequestBody Map<String, String> personMap, HttpServletResponse response) {
        logger.debug("About to create a user with values: {}.", personMap);
        Person person = new Person.PersonBuilder(personMap).build();
        personInitializer.getPersons().add(person);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return person;
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("userName") final String userName, HttpServletResponse response) {
        logger.debug("About to delete by userName {}.", userName);
        boolean found = false;
        for (Person person : personInitializer.getPersons()) {
            if (person.getUserName().equals(userName)) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                personInitializer.getPersons().remove(person);
                found = true;
                break;
            }
        }
        if(!found) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    }


}

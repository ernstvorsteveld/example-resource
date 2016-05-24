package nl.vorstdev.example.resource.resource;

import io.swagger.annotations.ApiOperation;
import nl.vorstdev.example.resource.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
@Controller
@RequestMapping(value = "persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController implements DocumentedPersonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonInitializer personInitializer;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Person> personList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "page", defaultValue = "10") Integer size) {
        logger.debug("About to get all persons.");
        return personInitializer.getPersons().subList(page - 1, size);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Person> person(
            @PathVariable("userName") final String userName) {
        logger.debug("About to load by userName {}.", userName);
        for (Person person : personInitializer.getPersons()) {
            if (person.getUserName().equals(userName)) {
                person.add(linkTo(methodOn(PersonController.class).person(userName)).withSelfRel());
                return ResponseEntity.ok(person);
            }
        }
        throw new NotFoundException(userName, Person.class);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "/", response = Person.class, code = 201)
    public ResponseEntity<Person> create(
            @RequestBody Map<String, String> personMap) {
        logger.debug("About to create a user with values: {}.", personMap);
        Person person = new Person.PersonBuilder(personMap).build();
        personInitializer.getPersons().add(person);
        Link link = linkTo(methodOn(PersonController.class).person(person.getUserName())).withSelfRel();
        return ResponseEntity.created(URI.create(link.getHref())).body(person);
    }

    @RequestMapping(value = "{userName}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Person> delete(
            @PathVariable("userName") final String userName) {
        logger.debug("About to delete by userName {}.", userName);
        Person deleted = null;
        for (Person person : personInitializer.getPersons()) {
            if (person.getUserName().equals(userName)) {
                deleted = person;
                personInitializer.getPersons().remove(person);
                break;
            }
        }
        if (deleted == null) {
            throw new NotFoundException(userName, Person.class);
        } else {
            return ResponseEntity.accepted().body(deleted);
        }
    }
}

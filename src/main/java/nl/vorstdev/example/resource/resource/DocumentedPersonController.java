package nl.vorstdev.example.resource.resource;

import io.swagger.annotations.*;
import nl.vorstdev.example.resource.domain.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by ernstvorsteveld on 06/02/16.
 */
@Api(tags = "Person CRUD Services", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DocumentedPersonController {

    String DOC_PARAM_USERNAME = "The username of the user to search for.";
    String PARAM_USERNAME = "userName";
    String QP_PAGE = "page";
    String DOC_QP_PAGE = "Page number, starts with 1, default value = 10";
    String QP_SIZE = "size";
    String DOC_QP_SIZE = "Page size, default value = 10";
    String DOC_PersonList = "Retrieve all available persons in a page manner. The response will " +
            "contain default the first page of 5 users.";
    String DOC_QPD_PAGE = "1";
    String DOC_QPD_SIZE = "5";
    String NICK_PERSONLIST = "Get persons";
    String NCIK_PERSON = "Get person a person by its user name";
    String NOTES_PERSON = "Retrieve one person by its username.";

    /**
     * Retrieve all users, paged.
     *
     * @param page The page to retrieve.
     * @param size The size of the page to retrieve.
     * @return
     */
    @ApiOperation(code = 200, httpMethod = "GET", value = "/", notes = DOC_PersonList, response = Person.class,
            responseContainer = "List", nickname = NICK_PERSONLIST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = QP_PAGE, paramType = "path", dataType = "int", value = DOC_QP_PAGE),
            @ApiImplicitParam(name = QP_SIZE, paramType = "path", dataType = "int", value = DOC_QP_SIZE)})
    @ApiResponse(code = 200, responseContainer = "List", response = Person.class, message = "Message for personList.")
    List<Person> personList(@RequestParam(value = QP_PAGE, required = false, defaultValue = DOC_QPD_PAGE)
                            int page,
                            @RequestParam(value = QP_SIZE, required = false, defaultValue = DOC_QPD_SIZE)
                            int size);

    /**
     * Retrieve a user by its username.
     *
     * @param userName The username of the user to search for.
     * @param response The servlet response.
     * @return The user when found.
     */
    @ApiOperation(code = 200, httpMethod = "GET", value = "/{userName}", notes = NOTES_PERSON,
            response = Person.class, nickname = NCIK_PERSON)
    @ApiResponse(code = 200, response = Person.class, message = "Message for person.", reference = "Person")
    Person person(
            @ApiParam(name = PARAM_USERNAME, value = DOC_PARAM_USERNAME, required = true, example = "username-1")
            @PathVariable("userName") final String userName,
            HttpServletResponse response);

    /**
     * Create a user.
     *
     * @param personMap The set of values.
     * @param response  The servlet response.
     * @return The created user.
     */
    @ApiOperation(value = "/", response = Person.class, code = 201)
    Person create(@RequestBody Map<String, String> personMap, HttpServletResponse response);

    /**
     * Delete a user with a username.
     *
     * @param userName The username of the user to delete.
     * @param response The servlet response.
     */
    @ApiOperation(code = 200, httpMethod = "DELETE", value = "/{userName}", notes = "Delete one person by its " +
            "username.",
            response = Void.class,
            nickname = "Delete a person by its user name")
    @ApiResponse(code = 200, response = Person.class, message = "Message for person.", reference = "Person")
    void delete(@PathVariable("userName") final String userName, HttpServletResponse response);

}

package nl.vorstdev.example.resource.resource;

import io.swagger.annotations.*;
import nl.vorstdev.example.resource.domain.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by ernstvorsteveld on 06/02/16.
 */
@Api(tags = "Person CRUD Services", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DocumentedPersonController {

    String DOC_GET_P_USERNAME = "The user name of the user to search for.";
    String DOC_DEL_P_USERNAME = "The user name of the user to delete.";
    String P_USERNAME = "userName";
    String QP_PAGE = "page";
    String DOC_QP_PAGE = "Page number, starts with 1, default value = 10";
    String QP_SIZE = "size";
    String DOC_QP_SIZE = "Page size, default value = 10";
    String DOC_PersonList = "Retrieve all available persons in a page manner. The response will " +
            "contain default the first page of 10 users (if present).";
    String DOC_QPD_PAGE = "1";
    String DOC_QPD_SIZE = "5";
    String NICK_PERSONLIST = "Get persons";
    String NICK_GET_PERSON = "Get person a person by its user name";
    String NOTES_GET = "Retrieve one person by its user name.";
    String DOC_RESPONSE_PERSONLIST_400 = "When requested page does not exist.";
    String PATH = "path";
    String QUERY = "query";
    String STRING = "string";
    String INT = "integer";
    String LIST = "List";
    String GET = "GET";
    String NOTES_DELETE = "Delete one person by its user name.";
    String MSG_USERNAME_NOT_FOUND = "User name not found";
    String NOTES_CREATE = "Create a user.";
    String NICK_CREATE_PERSON = "Create person";
    String MSG_PERSON_CREATE_NOT_ACCEPTABLE = "Person is not good enough or something.";
    String BODY = "body";
    String PERSON = "Person";
    String PD_PERSON = "Post data person.";

    /**
     * Retrieve all users, paged.
     *
     * @param page The page to retrieve.
     * @param size The size of the page to retrieve.
     * @return
     */
    @ApiOperation(code = 200, httpMethod = GET, value = "/", notes = DOC_PersonList, response = Person.class,
            responseContainer = LIST, nickname = NICK_PERSONLIST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = QP_PAGE, paramType = QUERY, dataType = INT, value = DOC_QP_PAGE,
                    defaultValue = DOC_QPD_PAGE),
            @ApiImplicitParam(name = QP_SIZE, paramType = QUERY, dataType = INT, value = DOC_QP_SIZE,
                    defaultValue = DOC_QPD_SIZE)})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = DOC_RESPONSE_PERSONLIST_400, response = Void.class)
    })
    List<Person> personList(
            Integer page,
            Integer size);

    /**
     * Retrieve a user by its username.
     *
     * @param userName The username of the user to search for.
     * @param response The servlet response.
     * @return The user when found.
     */
    @ApiOperation(code = 200, httpMethod = GET, value = "/{userName}", notes = NOTES_GET,
            response = Person.class, nickname = NICK_GET_PERSON)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = MSG_USERNAME_NOT_FOUND)
    })
    Person person(
            final String userName,
            HttpServletResponse response);

    /**
     * Create a user.
     *
     * @param personMap The set of values.
     * @param response  The servlet response.
     * @return The created user.
     */
    @ApiOperation(code = 201, httpMethod = "POST", value = "/", notes = NOTES_CREATE,
            response = Person.class, nickname = NICK_CREATE_PERSON)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = PD_PERSON, paramType = BODY, dataType = PERSON, value = DOC_QP_PAGE)})

    @ApiResponses(value = {
            @ApiResponse(code = 406, message = MSG_PERSON_CREATE_NOT_ACCEPTABLE)
    })
    Person create(
            Map<String, String> personMap,
            HttpServletResponse response);

    /**
     * Delete a user with a username.
     *
     * @param userName The username of the user to delete.
     * @param response The servlet response.
     */
    @ApiOperation(code = 200, httpMethod = "DELETE", value = "/{userName}", notes = NOTES_DELETE,
            response = Void.class,
            nickname = "Delete a person by its user name")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = P_USERNAME, paramType = PATH, dataType = STRING, value = DOC_DEL_P_USERNAME)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = Person.class, message = "Message for person.", reference = "Person"),
            @ApiResponse(code = 404, message = MSG_USERNAME_NOT_FOUND)})
    void delete(
            final String userName,
            HttpServletResponse response);

}

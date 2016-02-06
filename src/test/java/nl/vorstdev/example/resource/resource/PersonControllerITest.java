package nl.vorstdev.example.resource.resource;

import com.google.common.collect.Maps;
import nl.vorstdev.example.resource.Application;
import nl.vorstdev.example.resource.domain.Person;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by ernstvorsteveld on 17/01/16.
 */
@ActiveProfiles("dev")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class PersonControllerITest {

    public static final String USERNAME_8 = "username8";
    public static final String FIRSTNAME_A = "firstname_a";
    public static final String LASTNAME_A = "lastname_a";
    public static final String USERNAME_A = "username_a";
    @Autowired
    private DocumentedPersonController personController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(personController).build();
    }

    @Test
    public void should_get_all() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    public void should_retrieve_only_one() throws Exception {
        mockMvc.perform(get("/persons/{username}", USERNAME_8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName", is(USERNAME_8)));
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_create_and_delete() throws Exception {
        Map<String, String> personMap = expectUser(USERNAME_A, FIRSTNAME_A, LASTNAME_A);
        mockMvc.perform(post("/persons")
                .content(objectMapper.writeValueAsBytes(personMap))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName", is(USERNAME_A)));

        mockMvc.perform(delete("/persons/{username}", USERNAME_A))
                .andExpect(status().isAccepted());
    }

    private Map<String, String> expectUser(String userName, String firstName, String lastName) {
        Map<String, String> person = Maps.newHashMap();
        person.put(Person.FIRSTNAME, firstName);
        person.put(Person.LASTNAME, lastName);
        person.put(Person.USERNAME, userName);
        return person;
    }

}
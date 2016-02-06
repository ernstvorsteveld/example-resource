package nl.vorstdev.example.resource.resource;

import nl.vorstdev.example.resource.Application;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static nl.vorstdev.example.resource.resource.DocumentedPersonController.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ernstvorsteveld on 06/02/16.
 */
@ActiveProfiles("dev")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class DocsPersonControllerITest {

    @Rule
    public final RestDocumentation restDocumentation =
            new RestDocumentation(DocGeneratorSettings.DOC_PERSON_CONTROLLER_DIR);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    private static final String getUrl = "/persons?page={page}&size={size}";
    private static final String getUrlByUsername = "/persons/{userName}";

    @Test
    public void should_genereate_documentation_for_get_by_username() throws Exception {
        String userName = "username1";
        this.mockMvc.perform(get(getUrlByUsername, userName)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("get-person-by-username",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName(PARAM_USERNAME).description(DOC_PARAM_USERNAME))
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    public void should_genereate_documentation_for_get_all() throws Exception {
        this.mockMvc.perform(get(getUrl, 1, 2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("get-persons",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName(QP_PAGE).description(DOC_QP_PAGE),
                                parameterWithName(QP_SIZE).description(DOC_QP_SIZE))
                        )
                )
                .andExpect(status().isOk());
    }

}

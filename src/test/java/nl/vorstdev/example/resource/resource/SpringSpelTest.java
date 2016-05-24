package nl.vorstdev.example.resource.resource;

import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class SpringSpelTest {

    @Test
    public void should_generate_uuid_using_spel() {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext simpleContext = new StandardEvaluationContext();
        String value = (String) parser.parseExpression("T(java.util.UUID).randomUUID().toString()").getValue
                (simpleContext);
        assertThat(value.length(), is(greaterThan(4)));
        System.out.println(value);
    }
}

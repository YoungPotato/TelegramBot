package test;

import main_package.questions.Json;
import main_package.questions.Question;
import main_package.questions.QuestionsProvider;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ParserTest {

    @Mock
    private Json json;

    private QuestionsProvider questionsProvider;
    private Question question;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        String apiUrl = "";
        questionsProvider = new QuestionsProvider(apiUrl, json);
        question = new Question();
    }

    @org.junit.Test
    public void test_parser() throws IOException {
        String jsonResponse = "{\"ok\":true,\"data\":{\"question\":\"\\u0420\\u2063\\u0435\\u043a\\u0438 \\u0441 " +
                "\\u043a\\u0430\\u043a\\u0438\\u043c \\u2063\\u043d\\u0430\\u0437\\u0432\\u0430\\u043d\\u0438\\u0435" +
                "\\u043c \\u043d\\u0435\\u2063\\u0442 \\u043d\\u0430 \\u0442\\u0435\\u0440\\u0440\\u0438\\u0442\\u043e" +
                "\\u0440\\u0438\\u0438 \\u0420\\u043e\\u0441\\u0441\\u0438\\u0438?\",\"answers\":[\"\\u0421\\u043f\\" +
                "u0438\\u043d\\u0430\",\"\\u0428\\u0435\\u044f\",\"\\u0423\\u0441\\u0442\\u0430\",\"\\u041f\\u0430\\" +
                "u043b\\u0435\\u0446\"],\"id\":0}}";
        Mockito.doReturn(jsonResponse)
                .when(json).getJsonResponse(Mockito.anyString());
        question = questionsProvider.getQuestion(3);
        List<String> rightAnswers = Arrays.asList("Палец", "Уста", "Спина", "Шея");
        assert (question.question.equals("Реки с каким названием нет на территории России?"));
        assert (question.answers.containsAll(rightAnswers));
        assert (question.correctAnswer.equals("Спина"));
    }
}
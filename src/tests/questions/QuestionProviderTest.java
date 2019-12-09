package tests.questions;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import main.questions.HttpRequest;
import main.questions.JsonParser;
import main.questions.QnA;
import main.questions.QuestionsProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class QuestionProviderTest {
    @Mock
    private HttpRequest httpRequest;

    private QuestionsProvider questionsProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JsonParser jsonParser = new JsonParser();
        questionsProvider = new QuestionsProvider("", httpRequest, jsonParser);
    }

    @org.junit.Test
    public void QuestionTest() throws IOException {
        Mockito.when(httpRequest.getJsonResponse(Mockito.anyString())).thenReturn(getMockedResponse());

        String rightQuestion = "Чем мажут царапины?";
        List<String> rightAnswers = Arrays.asList("Чернилкой", "Зелёнкой", "Белилкой", "Краснёнкой");
        String rightCorrectAnswer = "Зелёнкой";
        QnA qnA = new QnA(rightQuestion, rightAnswers, rightCorrectAnswer);
        QnA testQnA = questionsProvider.getQuestion(2);
        testQnA(qnA, testQnA);
    }

    private String getMockedResponse() {
        return "{\"ok\":true,\"data\":{\"question\":\"\\u0427\\u2063\\u0435\\u043c \\u043c\\u0430\\u0436\\u0443\\u0442 " +
                "\\u0446\\u0430\\u2063\\u0440\\u2063\\u0430\\u043f\\u0438\\u043d\\u044b?\",\"answers\":[\"\\u0417\\u0435" +
                "\\u043b\\u0451\\u043d\\u043a\\u043e\\u0439\",\"\\u041a\\u0440\\u0430\\u0441\\u043d\\u0451\\u043d\\u043a" +
                "\\u043e\\u0439\",\"\\u0411\\u0435\\u043b\\u0438\\u043b\\u043a\\u043e\\u0439\",\"\\u0427\\u0435\\u0440\\" +
                "u043d\\u0438\\u043b\\u043a\\u043e\\u0439\"],\"id\":0}}";
    }

    private void testQnA(QnA qna, QnA testQnA) {
        assert (qna.getQuestion().equals(testQnA.getQuestion()));
        for (int i = 0; i < qna.getAnswers().size(); i++) {
            assert (qna.getAnswers().contains(testQnA.getAnswers().get(i)));
        }
        assert (qna.getCorrectAnswer().equals(testQnA.getCorrectAnswer()));
    }
}

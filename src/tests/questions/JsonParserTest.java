package tests.questions;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import main.questions.JsonParser;
import main.questions.QnA;

import java.util.Arrays;
import java.util.List;

public class JsonParserTest {
    @org.junit.Test
    public void parserTest() {
        JsonParser jsonParser = new JsonParser();
        String jsonResponse = "{\"ok\":true,\"data\":{\"question\":\"\\u0420\\u2063\\u0435\\u043a\\u0438 \\u0441 " +
                "\\u043a\\u0430\\u043a\\u0438\\u043c \\u2063\\u043d\\u0430\\u0437\\u0432\\u0430\\u043d\\u0438\\u0435" +
                "\\u043c \\u043d\\u0435\\u2063\\u0442 \\u043d\\u0430 \\u0442\\u0435\\u0440\\u0440\\u0438\\u0442\\u043e" +
                "\\u0440\\u0438\\u0438 \\u0420\\u043e\\u0441\\u0441\\u0438\\u0438?\",\"answers\":[\"\\u0421\\u043f\\" +
                "u0438\\u043d\\u0430\",\"\\u0428\\u0435\\u044f\",\"\\u0423\\u0441\\u0442\\u0430\",\"\\u041f\\u0430\\" +
                "u043b\\u0435\\u0446\"],\"id\":0}}";
        ImmutableTriple<String, List<String>, String> data = jsonParser.response2QnA(jsonResponse);
        QnA qnA = new QnA(data.left, data.middle, data.right);
        List<String> rightAnswers = Arrays.asList("Палец", "Уста", "Спина", "Шея");
        assert (qnA.getQuestion().equals("Реки с каким названием нет на территории России?"));
        assert (qnA.getAnswers().containsAll(rightAnswers));
        assert (qnA.getCorrectAnswer().equals("Спина"));
    }
}
package telegram_bot.questions;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionsProvider implements Questions {

    private String apiUrl;
    private HttpRequest httpRequest;
    private JsonParser jsonParser;

    public QuestionsProvider(String apiUrl, HttpRequest httpRequest, JsonParser jsonParser) {
        this.apiUrl = apiUrl;
        this.httpRequest = httpRequest;
        this.jsonParser = jsonParser;
    }

    @Override
    public QnA getQuestion(int level_difficulty) throws IOException {
        String apiUrl = String.format(this.apiUrl, level_difficulty);
        String response = httpRequest.getJsonResponse(apiUrl);
        ImmutableTriple<String, List<String>, String> data = jsonParser.response2QnA(response);
        return new QnA(data.left, shuffleAnswers(data.middle), data.right);
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }
}
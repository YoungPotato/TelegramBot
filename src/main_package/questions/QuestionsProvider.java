package main_package.questions;

import java.io.IOException;
import java.util.*;

public class QuestionsProvider implements Questions {

    private String apiUrl;
    private Json json;
    private JsonParser jsonParser;

    public QuestionsProvider(String apiUrl) {
        this.apiUrl = apiUrl;
        json = new Json();
        jsonParser = new JsonParser();
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }

    @Override
    public QnA getQuestion(int level_difficulty) throws IOException {
        String apiUrl = String.format(this.apiUrl, level_difficulty);
        String response = json.getJsonResponse(apiUrl);
        return jsonParser.response2QnA(response);
    }
}
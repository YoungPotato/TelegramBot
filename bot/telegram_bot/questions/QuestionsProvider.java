package main_package.questions;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionsProvider implements Questions {

    private String apiUrl;

    public QuestionsProvider(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public QnA getQuestion(int level_difficulty) throws IOException {
        String apiUrl = String.format(this.apiUrl, level_difficulty);
        String response = HttpRequest.getJsonResponse(apiUrl);
        ImmutableTriple<String, List<String>, String> data = JsonParser.response2QnA(response);
        return new QnA(data.left, shuffleAnswers(data.middle), data.right);
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }
}
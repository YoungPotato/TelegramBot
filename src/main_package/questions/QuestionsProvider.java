package main_package.questions;

import org.json.*;
import java.io.IOException;
import java.util.*;

public class QuestionsProvider implements Questions {

    private String apiUrl;
    private Json json;

    public QuestionsProvider(String apiUrl, Json json) {
        this.apiUrl = apiUrl;
        this.json = json;
    }

    private Question response2Question(String jsonResponse) {
        Question question = new Question();
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject data = obj.getJSONObject("data");
        question.question = data.getString("question").replace("\u2063", "");
        JSONArray answers = data.getJSONArray("answers");
        List<String> rawAnswers = new ArrayList<>();
        for (Object answer: answers)
            rawAnswers.add((String)answer);
        question.correctAnswer = rawAnswers.get(0);
        question.answers = shuffleAnswers(rawAnswers);
        return question;
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }

    @Override
    public Question getQuestion(int level_difficulty) throws IOException {
        String apiUrl = String.format(this.apiUrl, level_difficulty);
        return response2Question(json.getJsonResponse(apiUrl));
    }
}
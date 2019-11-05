package main_package.questions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JsonParser {
    public QnA response2QnA(String jsonResponse) {
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject data = obj.getJSONObject("data");
        String question = data.getString("question").replace("\u2063", "");
        JSONArray jAnswers = data.getJSONArray("answers");
        List<String> rawAnswers = new ArrayList<>();
        for (Object answer: jAnswers)
            rawAnswers.add((String)answer);
        String correctAnswer = rawAnswers.get(0);
        return new QnA(question, shuffleAnswers(rawAnswers), correctAnswer);
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }
}
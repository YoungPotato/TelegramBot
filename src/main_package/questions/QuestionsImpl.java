package main_package.questions;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class QuestionsImpl implements Questions {

    private String apiUrl;

    public QuestionsImpl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Question getQuestion(int level_difficulty) throws IOException {
        String apiUrl = String.format(this.apiUrl, level_difficulty);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = input.readLine();
        input.close();
        return response2Question(response);
    }

    private Question response2Question(String response) {
        Question question = new Question();
        JSONObject obj = new JSONObject(response);
        question.question = obj.getJSONObject("data").getString("question");
        JSONArray answers = obj.getJSONObject("data").getJSONArray("answers");
        List<String> rawAnswers = new ArrayList<>();
        for (Object answer: answers)
            rawAnswers.add(StringEscapeUtils.unescapeJava((String)answer));
        question.correctAnswer = rawAnswers.get(0);
        question.answers = shuffleAnswers(rawAnswers);
        return question;
    }

    private List<String> shuffleAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }
}
package main_package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Questions {
    public Question getQuestion(int level_difficulty) throws IOException {
        String url = String.format("https://engine.lifeis.porn/api/millionaire.php?q=%s", level_difficulty);
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = in.readLine();
        in.close();
        return response2Question(response);
    }

    private Question response2Question(String response){
        Question question = new Question();
        String regex = "\"question\":\".+?,\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            question.question = response.substring(matcher.start() + 12, matcher.end() - 3);
        }
        regex = "\"answers\":.+?]";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(response);
        String answers = "";
        while (matcher.find()) {
            answers = (response.substring(matcher.start() + 11, matcher.end() - 1));
        }
        String[] answersSplit = answers.split(",");
        for (String answer: answersSplit)
            question.answers.add(answer);
        question.correctAnswer = question.answers.get(1);
        return question;
    }
}
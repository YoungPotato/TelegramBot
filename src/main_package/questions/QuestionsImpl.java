package main_package.questions;
import org.apache.commons.lang.StringEscapeUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionsImpl implements Questions {

    @Override
    public main_package.questions.Question getQuestion(String apiUrl, int level_difficulty) throws IOException {

        String url = String.format(apiUrl, level_difficulty);
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String response = in.readLine();
        in.close();
        return response2Question(response);
    }

    private main_package.questions.Question response2Question(String response){
        main_package.questions.Question question = new main_package.questions.Question();
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
            answers = (response.substring(matcher.start() + 12, matcher.end() - 2));
        }
        String[] answersSplit = answers.split(",");
        List<String> rawAnswers = new ArrayList<>();
        for (String answer: answersSplit)
            rawAnswers.add(StringEscapeUtils.unescapeJava(answer
                    .replace("\\u2063", "")
                    .replace("\"", "")));
        question.correctAnswer = rawAnswers.get(0);
        question.answers = randomizeAnswers(rawAnswers);
        return question;
    }

    private List<String> randomizeAnswers(List<String> answers) {
        Collections.shuffle(answers, new Random());
        return answers;
    }
}
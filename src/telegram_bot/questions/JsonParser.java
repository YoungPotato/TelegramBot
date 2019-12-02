package telegram_bot.questions;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.lang3.tuple.*;

public class JsonParser {
    public ImmutableTriple<String, List<String>, String> response2QnA(String jsonResponse) {
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject data = obj.getJSONObject("data");
        String question = data.getString("question").replace("\u2063", "");
        JSONArray jAnswers = data.getJSONArray("answers");
        List<String> answers = new ArrayList<>();
        for (Object answer: jAnswers)
            answers.add((String)answer);
        String correctAnswer = answers.get(0);
        return new ImmutableTriple<>(question, answers, correctAnswer);
    }
}
package main_package.questions;
import java.io.IOException;

public interface Questions {

    main_package.questions.Question getQuestion(String apiUrl, int level_difficulty) throws IOException;
}

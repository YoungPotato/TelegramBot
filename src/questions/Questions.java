package questions;

import java.io.IOException;

public interface Questions {
    QnA getQuestion(int level_difficulty) throws IOException;
}

package main_package.questions;

import java.io.IOException;

public interface Questions {
    Question getQuestion(int level_difficulty) throws IOException;
}

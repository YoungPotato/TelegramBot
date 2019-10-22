package test;

import main_package.Logic;
import main_package.questions.Question;
import main_package.io.IO;
import main_package.questions.Questions;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.ArrayList;
import static org.mockito.Mockito.times;

public class Test {

    private static String ACTUAL_ANSWER = "4";
    private static String INCORRECT_ANSWER = "10";

    @Mock
    private Questions questions;
    @Mock
    private IO io;

    private Logic logic;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        logic = new Logic(questions, io, "");
    }

    @org.junit.Test
    public void test_with_correct_answer() throws IOException {

        Mockito.when(questions.getQuestion(Mockito.any(), Mockito.anyInt()))
                .thenReturn(getMockedQuestion());

        Mockito.when(io.read())
                .thenReturn(ACTUAL_ANSWER);

        logic.ShowQuestion();

        Mockito.verify(io, times(1)).correctAnswer();
    }

    @org.junit.Test
    public void test_with_incorrect_answer() throws IOException {

        Mockito.when(questions.getQuestion(Mockito.any(), Mockito.anyInt()))
                .thenReturn(getMockedQuestion());

        Mockito.when(io.read())
                .thenReturn(INCORRECT_ANSWER);

        logic.ShowQuestion();

        Mockito.verify(io, times(1)).incorrectAnswer();
        Mockito.verify(io, times(0)).correctAnswer();
    }

    private Question getMockedQuestion() {

        Question mockedQuestion = new Question();

        ArrayList<String> answers = new ArrayList<>();
        answers.add(ACTUAL_ANSWER);
        answers.add(INCORRECT_ANSWER);

        mockedQuestion.question = "Сколько сегодня пар?";
        mockedQuestion.answers = answers;
        mockedQuestion.correctAnswer = ACTUAL_ANSWER;

        return mockedQuestion;
    }
}

package test;

import main_package.Logic;
import main_package.io.IOImpl;
import main_package.questions.Question;
import main_package.questions.QuestionsProvider;
import main_package.io.Messages;

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
    private QuestionsProvider questions;

    @Mock
    private IOImpl io;

    private Logic logic;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        logic = new Logic(questions, io);
    }

    @org.junit.Test
    public void test_with_correct_answer() throws IOException {

        Mockito.when(questions.getQuestion(Mockito.anyInt()))
                .thenReturn(getMockedQuestion());

        Mockito.doReturn(ACTUAL_ANSWER)
                .when(io).read();

        logic.ShowQuestion();

        Mockito.verify(io, times(1)).write(Messages.CORRECT);
    }

    @org.junit.Test
    public void test_with_incorrect_answer() throws IOException {

        Mockito.when(questions.getQuestion(Mockito.anyInt()))
                .thenReturn(getMockedQuestion());

        Mockito.doReturn(INCORRECT_ANSWER)
                .when(io).read();

        logic.ShowQuestion();

        Mockito.verify(io, times(1)).write(Messages.INCORRECT);
        Mockito.verify(io, times(0)).write(Messages.CORRECT);
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

package tests.logic;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import main.Logic.HintsLogic;
import main.Players.Player;
import main.Players.PlayerStorage;
import main.bot.ChatId;
import main.questions.QnA;
import main.questions.QuestionsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HintsLogicTest {
    @Mock
    PlayerStorage playerStorage;

    @Mock
    QuestionsProvider questionsProvider;

    @Mock
    ChatId chatId;

    private HintsLogic hintsLogic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hintsLogic = new HintsLogic(playerStorage, questionsProvider);
    }

    @org.junit.Test
    public void delete2IncorrectAnswersTest() {
        Mockito.when(playerStorage.getPlayer(chatId)).thenReturn(getMockedPlayer());

        Player player = hintsLogic.delete2IncorrectAnswers(chatId);

        assert (player.getCurrentQnA().getAnswers().size() == 2);
        assert (!player.getHints().getDelete2IncorrectAnswers());
    }

    @org.junit.Test
    public void getLevelDifficultyTest() {
        int level = hintsLogic.getLevelDifficulty(4);
        assert (level == 4);

        level = hintsLogic.getLevelDifficulty(8);
        assert (level == 2);

        level = hintsLogic.getLevelDifficulty(12);
        assert (level == 3);
    }

    public Player getMockedPlayer() {
        Player player = new Player("3000 руб.");
        player.setCurrentQnA(getQnA());
        return player;
    }

    public QnA getQnA() {
        String rightQuestion = "Чем мажут царапины?";
        List<String> rightAnswers = new ArrayList<>(Arrays.asList("Чернилкой", "Зелёнкой", "Белилкой", "Краснёнкой"));
        String rightCorrectAnswer = "Зелёнкой";
        return new QnA(rightQuestion, rightAnswers, rightCorrectAnswer);
    }
}
package logic_test;

import Message.Amount;
import database.DatabaseOperation;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import Logic.Logic;
import Message.MessageFactory;
import Message.Messages;
import Players.Player;
import Players.PlayerStorage;
import bot.ChatId;
import io.TelegramIOImpl;
import questions.QnA;
import questions.QuestionsProvider;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class LogicTest {

    @Mock
    private TelegramIOImpl io;
    @Mock
    ChatId chatId;
    @Mock
    private QuestionsProvider questionsProvider;
    @Mock
    private MessageFactory messageBroker;
    @Mock
    PlayerStorage playerStorage;

    private Logic logic;
    private Amount amount;
    private DatabaseOperation databaseOperation;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        databaseOperation = new DatabaseOperation();
        MockitoAnnotations.initMocks(this);
        amount = new Amount();
        logic = new Logic(questionsProvider, playerStorage, messageBroker, io, amount, databaseOperation);
    }

    public Player getMockedPlayer() {
        Player player = new Player(3000);
        player.setCurrentQnA(getQnA());
        return player;
    }

    public SendMessage getMockedMessage(String text) {
        return new SendMessage().setText(text);
    }

    public QnA getQnA() {
        String rightQuestion = "Чем мажут царапины?";
        List<String> rightAnswers = Arrays.asList("Чернилкой", "Зелёнкой", "Белилкой", "Краснёнкой");
        String rightCorrectAnswer = "Зелёнкой";
        return new QnA(rightQuestion, rightAnswers, rightCorrectAnswer);
    }

    @org.junit.Test
    public void writeStartTest() throws Exception {
        String text = "Вы попали в игру \"Как стать миллионером\". Чат-бот будет выдавать вам вопросы, " +
                "на которые Вы должны постараться дать правильные ответы. Удачи!";
        Mockito.when(messageBroker.getMessage(Messages.START)).thenReturn(getMockedMessage(text));

        logic.processMessage("/start", chatId);

        Mockito.verify(messageBroker).getMessage(Messages.START);
        Mockito.verify(io).write(messageBroker.getMessage(Messages.START), chatId);
    }

    @org.junit.Test
    public void writeHelpTest() throws Exception {
        String text = "Отвечайте на вопросы бота либо цифрами, либо словами";
        Mockito.when(messageBroker.getMessage(Messages.HELP)).thenReturn(getMockedMessage(text));

        logic.processMessage("/help", chatId);

        Mockito.verify(messageBroker).getMessage(Messages.HELP);
        Mockito.verify(io).write(messageBroker.getMessage(Messages.HELP), chatId);
    }

    @org.junit.Test
    public void writeAmountTest() throws Exception {
        logic.processMessage("/game", chatId);

        Mockito.verify(messageBroker).getAmountButtons(amount.getStrAmount());
        Mockito.verify(io).write(messageBroker.getAmountButtons(amount.getStrAmount()), chatId);
    }

    @org.junit.Test
    public void writeCorrectTest() throws Exception {
        String text = "Верно!";
        Mockito.when(messageBroker.getMessage(Messages.CORRECT)).thenReturn(getMockedMessage(text));
        Player mockedPlayer = getMockedPlayer();
        Mockito.when(playerStorage.getPlayer(chatId)).thenReturn(mockedPlayer);
        Mockito.when(questionsProvider.getQuestion(Mockito.anyInt())).thenReturn(getQnA());

        logic.processMessage("Зелёнкой", chatId);

        Mockito.verify(messageBroker).getMessage(Messages.CORRECT);
        Mockito.verify(io).write(messageBroker.getMessage(Messages.CORRECT), chatId);
        Mockito.verify(io).write(messageBroker.getMessage(mockedPlayer.getCurrentQnA(), mockedPlayer.getHints(), mockedPlayer.getCountCorrectAnswers()), chatId);

        assert (mockedPlayer.getCountCorrectAnswers() == 1);
    }

    @org.junit.Test
    public void writeIncorrectTest() throws Exception {
        String text = "Неверно!";
        Mockito.when(messageBroker.getMessage(Messages.INCORRECT)).thenReturn(getMockedMessage(text));
        Player mockedPlayer = getMockedPlayer();
        Mockito.when(playerStorage.getPlayer(chatId)).thenReturn(mockedPlayer);

        logic.processMessage("Белилкой", chatId);

        Mockito.verify(messageBroker).getMessage(Messages.INCORRECT);
        Mockito.verify(io).write(messageBroker.getMessage(Messages.INCORRECT), chatId);
        Mockito.verify(io).write(messageBroker.getMessage(mockedPlayer.getCurrentQnA(), mockedPlayer.getHints(), mockedPlayer.getCountCorrectAnswers()), chatId);

        assert (mockedPlayer.getCountCorrectAnswers() == 0);
    }

    @org.junit.Test
    public void writeCongratulationsTest() throws Exception {
        String text = "Поздравляем, Вы победили";
        Mockito.when(messageBroker.getMessage(Messages.CONGRATULATIONS)).thenReturn(getMockedMessage(text));
        Player mockedPlayer = getMockedPlayer();
        for (int i = 0; i < 14; i++)
            mockedPlayer.addCountCorrectAnswers();
        Mockito.when(playerStorage.getPlayer(chatId)).thenReturn(mockedPlayer);

        logic.processMessage("Зелёнкой", chatId);

        Mockito.verify(messageBroker).getMessage(Messages.CONGRATULATIONS);
        Mockito.verify(io).write(messageBroker.getMessage(Messages.CONGRATULATIONS), chatId);

        assert (mockedPlayer.getCountCorrectAnswers() == 15);
    }
}

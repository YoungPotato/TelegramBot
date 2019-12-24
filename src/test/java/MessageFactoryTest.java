import Hints.Hints;
import Message.Amount;
import Message.Buttons;
import Message.MessageFactory;
import Players.Player;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import questions.QnA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageFactoryTest {
    private Buttons buttons = new Buttons();
    private MessageFactory messageFactory = new MessageFactory(buttons);

    @org.junit.Test
    public void getMessageTest() {
        Player player = getMockedPlayer();

        SendMessage message = messageFactory.getMessage(player.getCurrentQnA(), player.getHints(), player.getCountCorrectAnswers());
        assert (message.getText().equals(String.format("Вопрос №%1d \n\n %2s", 1, player.getCurrentQnA().getQuestion())));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup(player.getCurrentQnA(), player.getHints()));
        assert(message.getReplyMarkup().equals(sendMessage.getReplyMarkup()));

    }

    @org.junit.Test
    public void getAmountButtons() {
        Amount amount = new Amount();
        SendMessage message = messageFactory.getAmountButtons(amount.getStrAmount());

        assert (message.getText().equals("Выберите несгораемую сумму:"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(Lists.reverse(buttons.getInlineKeyboard(amount.getStrAmount())));
        SendMessage sendMessage = new SendMessage().setText("Выберите несгораемую сумму:");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        assert (message.getReplyMarkup().equals(sendMessage.getReplyMarkup()));
    }

    private Player getMockedPlayer() {
        Player player = new Player(3000);
        player.setCurrentQnA(getQnA());
        return player;
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup(QnA qnA, Hints hints) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = getInlineKeyboard(qnA.getAnswers());
        rowList.add(getHintKeyboard(hints));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private QnA getQnA() {
        String rightQuestion = "Чем мажут царапины?";
        List<String> rightAnswers = Arrays.asList("Чернилкой", "Зелёнкой", "Белилкой", "Краснёнкой");
        String rightCorrectAnswer = "Зелёнкой";
        return new QnA(rightQuestion, rightAnswers, rightCorrectAnswer);
    }

    private List<List<InlineKeyboardButton>> getInlineKeyboard(List<String> data) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(String.format("%1d. %2s", i + 1, data.get(i).trim()));
            inlineKeyboardButton.setCallbackData(data.get(i));
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }
        return rowList;
    }

    private List<InlineKeyboardButton> getHintKeyboard(Hints hints) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        List<ImmutablePair<String, String>> a = hints.getAvailableHints();
        for (ImmutablePair<String, String> b : a) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(b.right);
            inlineKeyboardButton.setCallbackData(b.left);
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        keyboardButtonsRow.add(getTakeMoneyButton());
        return keyboardButtonsRow;
    }

    private InlineKeyboardButton getTakeMoneyButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("money");
        inlineKeyboardButton.setCallbackData("/takeMoney");
        return inlineKeyboardButton;
    }
}

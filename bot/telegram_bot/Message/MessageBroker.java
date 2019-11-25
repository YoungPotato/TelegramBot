package telegram_bot;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.tuple.ImmutablePair;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import telegram_bot.Hints.Hints;
import telegram_bot.io.Messages;
import telegram_bot.questions.QnA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageBroker {
    public final List<String> amount = Arrays.asList("500 руб.", "1000 руб.", "2000 руб.", "3000 руб.", "5000 руб.", "10 000 руб.",
            "15 000 руб.", "25 000 руб.", "50 000 руб.", "100 000 руб.", "200 000 руб.", "400 000 руб.", "800 000 руб.",
            "1 500 000 руб.", "3 000 000 руб.");

    public SendMessage getMessage(Messages messages) throws Exception {
        switch (messages) {
            case HELP:
                return createMessage("Отвечайте на вопросы бота либо цифрами, либо словами");
            case START:
                return createMessage("Вы попали в игру \"Как стать миллионером\". Чат-бот будет выдавать вам вопросы, " +
                        "на которые Вы должны постараться дать правильные ответы. Удачи!");
            case CORRECT:
                return createMessage("Верно!");
            case INCORRECT:
                return createMessage("Неверно!");
            case GAMEOVER:
                return createMessage("К сожалению, Вы проиграли!");
            case CONGRATULATIONS:
                return createMessage("Поздравляем, Вы победили");
            default:
                throw new Exception();
        }
    }

    public SendMessage createMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage getMessage(Player player) {
        QnA qnA = player.getCurrentQnA();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = getInlineKeyboard(qnA.getAnswers());
        rowList.add(getHintKeyboard(player.getHints()));
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sendMessage = new SendMessage().setText(String.format("Вопрос №%1d \n\n %2s", player.getCountCorrectAnswers() + 1, qnA.getQuestion()));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage getAmount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(Lists.reverse(getInlineKeyboard(amount)));
        SendMessage sendMessage = new SendMessage().setText("Выберите несгораемую сумму:");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
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
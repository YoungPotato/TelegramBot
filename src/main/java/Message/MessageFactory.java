package Message;

import Hints.Hints;
import com.google.common.collect.Lists;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import questions.QnA;

import java.util.List;

public class MessageFactory {
    private Buttons buttons;

    public MessageFactory(Buttons buttons) {
        this.buttons = buttons;
    }

    public SendMessage getMessage(Messages messages) throws Exception {
        switch (messages) {
            case HELP:
                return createSimpleMessage("Отвечайте на вопросы бота либо цифрами, либо словами");
            case START:
                return createSimpleMessage("Вы попали в игру \"Как стать миллионером\". Чат-бот будет выдавать вам вопросы, " +
                        "на которые Вы должны постараться дать правильные ответы. Удачи!");
            case CORRECT:
                return createSimpleMessage("Верно!");
            case INCORRECT:
                return createSimpleMessage("Неверно!");
            case GAMEOVER:
                return createSimpleMessage("К сожалению, Вы проиграли!");
            case CONGRATULATIONS:
                return createSimpleMessage("Поздравляем, Вы победили");
            default:
                throw new Exception();
        }
    }

    public SendMessage createSimpleMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage getMessage(QnA qnA, Hints hints, int countCorrectAnswer) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = buttons.getInlineKeyboard(qnA.getAnswers());
        rowList.add(buttons.getHintKeyboard(hints));
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sendMessage = new SendMessage().setText(String.format("Вопрос №%1d \n\n %2s", countCorrectAnswer + 1, qnA.getQuestion()));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage getAmountButtons(List<String> amount) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(Lists.reverse(buttons.getInlineKeyboard(amount)));
        SendMessage sendMessage = new SendMessage().setText("Выберите несгораемую сумму:");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
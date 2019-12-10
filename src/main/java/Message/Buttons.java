package Message;

import Hints.Hints;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    private InlineKeyboardButton getTakeMoneyButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("money");
        inlineKeyboardButton.setCallbackData("/takeMoney");
        return inlineKeyboardButton;
    }

    public List<InlineKeyboardButton> getHintKeyboard(Hints hints) {
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

    public List<List<InlineKeyboardButton>> getInlineKeyboard(List<String> data) {
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
}

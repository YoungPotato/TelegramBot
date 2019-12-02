package telegram_bot.Hints;

public abstract class Hint {
    private Boolean value = true;

    public Boolean getValue() {
        return value;
    }

    public void setValue() {
        value = false;
    }
}

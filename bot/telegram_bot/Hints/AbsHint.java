package telegram_bot.Hints;

public abstract class Hint {
    private Boolean value = true;
    private Boolean used = false;

    public Boolean getValue() {
        return value;
    }
    public Boolean getUsed() {
        return used;
    }

    public void setValue() {
        value = false;
    }
    public void setUsed() {
        used = true;
    }
}

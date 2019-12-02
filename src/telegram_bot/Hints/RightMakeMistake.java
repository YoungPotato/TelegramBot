package telegram_bot.Hints;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class RightMakeMistake extends Hint implements IHint {
    private Boolean used = false;

    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/rightMakeMistake", "X2");
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed() {
        used = true;
    }
}

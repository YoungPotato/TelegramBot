package telegram_bot.Hints;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Delete2IncorrectAnswers extends Hint implements IHint {

    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/delete2IncorrectAnswers", "50/50");
    }
}

package main.Hints;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class ReplaceQuestion extends Hint implements IHint {
    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/replaceQuestion", "?");
    }
}

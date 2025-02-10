package fr.lifesteal.respectguard.business.object;

import java.util.Set;

public class MessageAnalysesResult {
    private final boolean isHarmful;
    private final Set<String> harmfulCategories;

    public MessageAnalysesResult(boolean isHarmful, Set<String> harmfulCategories) {
        this.isHarmful = isHarmful;
        this.harmfulCategories = harmfulCategories;
    }

    public boolean isHarmful() {
        return isHarmful;
    }

    public Set<String> getHarmfulCategories() {
        return harmfulCategories;
    }
}

package ai.fuzzy_rulebased_system.Fuzzification;

import java.util.ArrayList;
import java.util.List;

public class LinguisticTag {
    private String name;
    private List<Line> linesList;

    public LinguisticTag() {
        linesList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Line> getLinesList() {
        return linesList;
    }

    public void setLinesList(List<Line> linesList) {
        this.linesList = linesList;
    }
}

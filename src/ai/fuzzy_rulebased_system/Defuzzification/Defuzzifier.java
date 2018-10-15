package ai.fuzzy_rulebased_system.Defuzzification;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzification.LinguisticTag;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;

import java.util.HashMap;
import java.util.List;

public class Defuzzifier {

    public void defuzzify (HashMap<String, Double> fuzzyOutput) {
        FileManager fileManager = new FileManager();
        for(String tag : fuzzyOutput.keySet()) {
            for(LinguisticTag lT : fileManager.getTagsOfOutputVariable()) {

            }
        }
    }

}

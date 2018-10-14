package ai.fuzzy_rulebased_system;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzifier.Line;
import ai.fuzzy_rulebased_system.Fuzzifier.LinguisticTag;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;

public class Main {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
//        fileManager.loadLinguisticVariablesDataFromTextFile("linguistic_variables");
//        fileManager.printLinguisticVariablesFiles();
        for(RealVariable r : fileManager.getRealVariables()) {
            System.out.println("Var: "+ r.getName());
        }

        int tagIndex = 5;
        if(tagIndex < FileManager.TAGS_BY_VARIABLE) {
            LinguisticTag linguisticTag = fileManager.getLinguisticTag(1, tagIndex);
            if (linguisticTag != null) {
                System.out.println("NAME: " + linguisticTag.getName());
                for (Line l : linguisticTag.getLinesList()) {
                    System.out.println("L: (" + l.getPoint_a().getX() + ", " + l.getPoint_a().getY() + ") - " +
                                          "(" + l.getPoint_b().getX() + ", " + l.getPoint_b().getY() + ")");
                }
            } else {
                System.out.println("NULL");
            }
        }

    }
}

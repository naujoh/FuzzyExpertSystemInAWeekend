package ai.fuzzy_rulebased_system.Fuzzifier;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fuzzifier {

    public List<FuzzyVariable> fuzzify(List<RealVariable> rVariables) {
        List<FuzzyVariable> fuzzyVariables = new ArrayList<>();
        FuzzyVariable fuzzyVariable;
        LinguisticTag lTag;
        FileManager fm = new FileManager();
        HashMap<String, Double> tag;
        int indexTag = 0;
        for(RealVariable rV : rVariables) {
            while(indexTag < FileManager.TAGS_BY_VARIABLE) {
                lTag = fm.getLinguisticTag(rV.getVarID(), indexTag);
                if(lTag!=null) {
                    for(Line l : lTag.getLinesList()) {
                        fuzzyVariable = new FuzzyVariable();
                        fuzzyVariable.setVarID(rV.getVarID());
                        tag = new HashMap<>();
                        if(l.getPoint_a().getX() <= rV.getValue() && l.getPoint_b().getX() >= rV.getValue()) {
                            tag.put(lTag.getName(), getMembership(l, rV.getValue()));
                            fuzzyVariable.getMembershipByTag().add(tag);
                        } else {
                            tag.put(lTag.getName(), 0d);
                            fuzzyVariable.getMembershipByTag().add(tag);
                        }
                        fuzzyVariables.add(fuzzyVariable);
                    }
                }
                indexTag++;
            }
            indexTag = 0;
        }
        return fuzzyVariables;
    }

    /**
     * Using : y = ((x - x1)/(x2 - x1)) * (y2 - y1) + y1
     * @param line
     * @param xValue
     * @return
     */
    public double getMembership(Line line, double xValue) {
        return ((xValue - line.getPoint_a().getX()) / (line.getPoint_b().getX() - line.getPoint_a().getX())) * (line.getPoint_b().getY() - line.getPoint_a().getY()) + line.getPoint_a().getY();
    }
}

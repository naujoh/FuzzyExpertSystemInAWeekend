package ai.fuzzy_rulebased_system.Fuzzification;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;

import java.util.ArrayList;
import java.util.List;

public class Fuzzifier {

    public List<FuzzyVariable> fuzzify(List<RealVariable> rVariables) {
        List<FuzzyVariable> fuzzyVariables = new ArrayList<>();
        FuzzyVariable fuzzyVariable;
        LinguisticTag lTag;
        FileManager fm = new FileManager();
        boolean valueInRange = false;
        int tagIndex = 0;
        for(RealVariable rV : rVariables) {
            while(tagIndex < FileManager.TAGS_BY_VARIABLE) {
                lTag = fm.getLinguisticTag(rV.getVarID(), tagIndex);
                if(lTag!=null) {
                    fuzzyVariable = new FuzzyVariable();
                    fuzzyVariable.setVarID(rV.getVarID());
                    for(Line l : lTag.getLinesList()) {
                        if(l.getPoint_a().getX() <= rV.getValue() && l.getPoint_b().getX() >= rV.getValue()) {
                            fuzzyVariable.getMembershipByTag().put(lTag.getName(), getMembership(l, rV.getValue()));
                            valueInRange = true;
                        }
                    }
                    if(!valueInRange) fuzzyVariable.getMembershipByTag().put(lTag.getName(), 0d);
                    fuzzyVariables.add(fuzzyVariable);
                    valueInRange = false;
                }
                tagIndex++;
            }
            tagIndex = 0;
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

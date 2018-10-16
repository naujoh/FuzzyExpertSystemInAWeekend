package ai.fuzzy_rulebased_system.Defuzzification;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzification.Line;
import ai.fuzzy_rulebased_system.Fuzzification.LinguisticTag;
import ai.fuzzy_rulebased_system.Fuzzification.Point;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;

import java.util.HashMap;
import java.util.List;

public class Defuzzifier {

    private List<IntersectionPoint> iPoints;

    public Defuzzifier() {
        IntersectionPoint intersectionPoint = new IntersectionPoint();
        iPoints = intersectionPoint.getIntersectionPoints();
    }

    public Double defuzzify (HashMap<String, Double> fuzzyOutput) {
        FileManager fileManager = new FileManager();
        List<LinguisticTag> lTags = fileManager.getTagsOfOutputVariable();
        Point p;
        double x = 0, a = 0, b = 0, inc = 0.001, actualMembership = 0, mem, nextMem = 0, xLim;
        for(int i = 0; i < lTags.size(); i++) {
            for(String tag : fuzzyOutput.keySet()) {
                if(tag.equals(lTags.get(i).getName())) {
                    actualMembership = fuzzyOutput.get(tag);
                    for(Line l : lTags.get(i).getLinesList()) {
                        if(l.getPoint_a().getY() != l.getPoint_b().getY()) {
                            if(l!=lTags.get(i).getLinesList().get(lTags.get(i).getLinesList().size()-1)) {
                                while(l.getPoint_a().getX() <= x && l.getPoint_b().getX() >= x) {
                                    mem = getMembership(l,x);
                                    if(mem<=actualMembership) {
                                        a+=x*mem;
                                        b+=mem;
                                    } else {
                                        a+=x*actualMembership;
                                        b+=actualMembership;
                                    }
                                    x+=inc;
                                }
                            } else {
                                if(lTags.size()<i+1) {
                                    p = getIntersectionPoint(lTags.get(i), lTags.get(i+1));
                                    nextMem = getMemebershipOfNextTag(lTags.get(i+1),fuzzyOutput);
                                    if(p.getY()<actualMembership && p.getY() < nextMem) {
                                        while (x <= p.getX()) {
                                            a += x*getMembership(l,x);
                                            b += getMembership(l,x);
                                            x+=inc;
                                        }
                                    } else if(p.getY() > actualMembership && p.getY() > nextMem) {
                                        xLim = getX(lTags.get(i+1).getLinesList().get(0), actualMembership);
                                        while (x <= xLim) {
                                            a+= x*actualMembership;
                                            b+=actualMembership;
                                            x+=inc;
                                        }
                                    } else {
                                        while(x < p.getX()) {
                                            a += x*getMembership(l,x);
                                            b += getMembership(l,x);
                                            x+=inc;
                                        }
                                    }
                                }
                            }
                        } else {
                            while (l.getPoint_a().getX() <= x && l.getPoint_b().getX() >= x) {
                                a+=x*actualMembership;
                                b+=actualMembership;
                                x+=inc;
                            }
                        }
                    }
                }
            }
        }
        return a/b;
    }

    private Point getIntersectionPoint (LinguisticTag actualTag, LinguisticTag nextTag) {
        if(iPoints!=null) {
            for(IntersectionPoint iPoint : iPoints) {
                if(iPoint.getTagA().equals(actualTag.getName()) && iPoint.getTagB().equals(nextTag.getName())) {
                    return iPoint.getPoint();
                }
            }
        }
        return null;
    }

    private double getMembership(Line line, double xValue) {
        return ((xValue - line.getPoint_a().getX()) / (line.getPoint_b().getX() - line.getPoint_a().getX())) * (line.getPoint_b().getY() - line.getPoint_a().getY()) + line.getPoint_a().getY();
    }

    private double getX(Line line, double mem) {
        return ((mem - line.getPoint_a().getY())/(line.getPoint_b().getY()-line.getPoint_a().getY()))*(line.getPoint_b().getX()-line.getPoint_a().getX()) + line.getPoint_a().getX();
    }

    private Double getMemebershipOfNextTag(LinguisticTag nextTag, HashMap<String, Double> fuzzyOutput) {
        for(String tag : fuzzyOutput.keySet()) {
            if(nextTag.getName().equals(tag)) {
                return fuzzyOutput.get(tag);
            }
        }
        return 0d;
    }
}

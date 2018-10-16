package ai.fuzzy_rulebased_system.Defuzzification;

import ai.fuzzy_rulebased_system.Fuzzification.Point;

import java.util.ArrayList;
import java.util.List;

public class IntersectionPoint {
    private String tagA;
    private String tagB;
    private Point point;

    public IntersectionPoint(String tagA, String tagB, Point point) {
        this.tagA = tagA;
        this.tagB = tagB;
        this.point = point;
    }

    public IntersectionPoint() {}

    public String getTagA() {
        return tagA;
    }

    public void setTagA(String tagA) {
        this.tagA = tagA;
    }

    public String getTagB() {
        return tagB;
    }

    public void setTagB(String tagB) {
        this.tagB = tagB;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<IntersectionPoint> getIntersectionPoints() {
        List<IntersectionPoint> iPoints = new ArrayList<>();
        iPoints.add(new IntersectionPoint("REPROBADO", "CASI_APR",  new Point(51.82,0.09)));
        iPoints.add(new IntersectionPoint("CASI_APR", "APR_ESF",    new Point(61.18,0.09)));
        iPoints.add(new IntersectionPoint("APR_ESF", "APROBADO",    new Point(76.89,0.06)));
        iPoints.add(new IntersectionPoint("APROBADO", "APR_MERITO", new Point(83.17,0.04)));
        return iPoints;
    }
}

package ai.fuzzy_rulebased_system.Fuzzification;

public class Line {
    private Point point_a;
    private Point point_b;

    public Line(Point a, Point b) {
        this.point_a = a;
        this.point_b = b;
    }

    public Point getPoint_a() { return point_a; }

    public void setPoint_a(Point point_a) { this.point_a = point_a; }

    public Point getPoint_b() { return point_b; }

    public void setPoint_b(Point point_b) { this.point_b = point_b; }
}

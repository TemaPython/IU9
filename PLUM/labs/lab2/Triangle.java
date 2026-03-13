import java.lang.Math;

public class Triangle {
    private Point p1, p2, p3;
    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }


    public Point getP1() { return p1; }
    public Point getP2() { return p2; }
    public Point getP3() { return p3; }

    private double getDist(Point poi1, Point poi2) {
        return Math.sqrt(
                Math.pow(poi1.getX() - poi2.getX(), 2) +
                Math.pow(poi1.getY() - poi2.getY(), 2) +
                Math.pow(poi1.getZ() - poi2.getZ(), 2));
    }

    public double getSquare() {
        double a = getDist(p1, p2);
        double b = getDist(p2, p3);
        double c = getDist(p3, p1);
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public String toString() {
        return "("+p1.getX()+", "+p1.getY()+", "+p1.getZ()+"),\n" +
                "("+p2.getX()+", "+p2.getY()+", "+p2.getZ()+"),\n" +
                "("+p3.getX()+", "+p3.getY()+", "+p3.getZ()+")";
    }
}

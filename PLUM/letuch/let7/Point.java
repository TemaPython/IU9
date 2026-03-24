public class Point {
    private double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double dist() {
        return Math.sqrt(x*x + y*y);
    }
    public String toString() {
        return String.format("(%f,␣%f)", x, y);
    }
}
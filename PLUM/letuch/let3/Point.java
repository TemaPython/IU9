import java.util.Date;

import static java.lang.Math.*;
import java.util.Date;

public class Point {

    double x, y;
    static Date timeStart;
    Date timeStartObj;

    static {
        timeStart = new Date();
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        timeStartObj = new Date();
    }

    public static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }

    public String gtTime() {
        return String.valueOf(timeStartObj.getTime() - timeStart.getTime());
    }
}

import java.util.Date;

import static java.lang.Math.*;

public class Ruler {
    static Date timeStart;
    Date timeStartObj;

    static {
        timeStart = new Date();
    }
    public Ruler() {
        timeStartObj = new Date();
    }
    public double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }

    public String gtTime() {
        return String.valueOf(timeStartObj.getTime() - timeStart.getTime());
    }
}

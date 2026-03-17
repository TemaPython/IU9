import java.lang.Math;
import java.util.Date;

public class Body {
    String name;
    private final double x, y, z, v1, v2, v3, m;
    static Date timeStart;
    Date timeStartObj;

    static {
        timeStart = new Date();
    }

    public Body(String name, double x, double y, double z,
                double v1, double v2, double v3, double m) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.m = m;
        timeStartObj = new Date();
    }

    public Body(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        v1 = (10000 + 10000) * Math.random() - 10000;
        v2 = (10000 + 10000) * Math.random() - 10000;
        v3 = (10000 + 10000) * Math.random() - 10000;
        m = 1000000000 * Math.random();
        timeStartObj = new Date();
    }


    public Body(String name) {
        this.name = name;
        x = (100 + 100) * Math.random() - 100;
        y = (100 + 100) * Math.random() - 100;
        z = (100 + 100) * Math.random() - 100;
        v1 = (10000 + 10000) * Math.random() - 10000;
        v2 = (10000 + 10000) * Math.random() - 10000;
        v3 = (10000 + 10000) * Math.random() - 10000;
        m = 1000000000 * Math.random();
        timeStartObj = new Date();
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getZ() {return z;}
    public double getV1() {return v1;}
    public double getV2() {return v2;}
    public double getV3() {return v3;}
    public double getM() {return m;}

    public String gtTime() {
        return String.valueOf(timeStartObj.getTime() - timeStart.getTime());
    }
}

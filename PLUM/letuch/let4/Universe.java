import java.util.Date;

public class Universe {
    Body[] parts;
    int ln;
    static Date timeStart;
    Date timeStartObj;
    final double G = 6.67 * Math.pow(10, -11);

    static {
        timeStart = new Date();
    }

    public Universe(int n) {
        parts = new Body[n];
        ln = 0;
        timeStartObj = new Date();
    }

    public void addPart(Body... objs) {
        for (Body obj : objs) {
            if (ln == parts.length) {
                System.out.println("University overflowed");
                return;
            }
            parts[ln] = obj;
            ln++;
        }
    }

    public double[] getMeanV() {
        int n = parts.length;
        double sumV1, sumV2, sumV3;
        sumV1 = sumV2 = sumV3 = 0;
        for (Body obj : parts) {
            sumV1 += obj.getV1();
            sumV2 += obj.getV2();
            sumV3 += obj.getV3();
        }
        double v1 = sumV1/n, v2 = sumV2/n, v3 = sumV3/n;
        double norm = Math.sqrt(Math.pow(v1, 2) + Math.pow(v2, 2) + Math.pow(v3, 2));
        return new double[]{v1/norm, v2/norm, v3/norm};
    }

    public double getF(Body obj) {
        double f1, f2, f3, f;
        double m = obj.getM();
        double x = obj.getX();
        double y = obj.getY();
        double z = obj.getZ();
        f1 = f2 = f3 = 0;
        double dx, dy, dz, r;
        for (Body i : parts) {
            dx = x - i.getX();
            dy = y - i.getY();
            dz = z - i.getZ();
            r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
            if (r == 0) {continue;}
            f = G * m * i.getM() / Math.pow(r, 2);
            f1 += f * dx / r;
            f2 += f * dy / r;
            f3 += f * dz / r;
        }
        return Math.sqrt(Math.pow(f1, 2) + Math.pow(f2, 2) + Math.pow(f3, 2));
    }

    public String gtTime() {
        return String.valueOf(timeStartObj.getTime() - timeStart.getTime());
    }
}
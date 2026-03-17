import java.util.Date;

public class Universe {
    Stack<Body> parts;
    public Universe() {
        parts = new Stack<Body>();
    }

    public void addPart(Body... objs) {
        for (Body obj : objs) {
            parts.push(obj);
        }
    }
    public int getLen() {
        return parts.genLen();
    }
    public double[] getSumR() {
        double sumX, sumY, sumZ;
        sumX = sumY = sumZ = 0;
        Stack<Body> parts1 = new Stack<Body>();
        while (!parts.empty()) {
            Body obj = parts.pop();
            sumX += obj.getX();
            sumY += obj.getY();
            sumZ += obj.getZ();
            parts1.push(obj);
        }
        while (!parts1.empty()) {
            parts.push(parts1.pop());
        }
        return new double[]{sumX, sumY, sumZ};
    }

}

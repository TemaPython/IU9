import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        Universe universe1 = new Universe();
        for (int i = 0; i < 3; i++) {
            universe1.addPart(new Body(String.valueOf(i)));
        }
        Universe universe2 = new Universe();
        for (int i = 0; i < 4; i++) {
            universe2.addPart(new Body(String.valueOf(i)));
        }
        Universe universe3 = new Universe();
        for (int i = 0; i < 2; i++) {
            universe3.addPart(new Body(String.valueOf(i)));
        }


        Stack<Universe> s = new Stack<Universe>();
        s.push(universe1);
        s.push(universe2);
        s.push(universe3);
        double[] sum = {0, 0, 0};
        int n = 0;
        while(!s.empty()) {
            Universe u = s.pop();
            double[] r = u.getSumR();
            sum[0] += r[0];
            sum[1] += r[1];
            sum[2] += r[2];
            n+= u.getLen();
        }
        System.out.println("( " + sum[0]/n + " " + sum[1]/n + " " + sum[2]/n + " )");
    }
}

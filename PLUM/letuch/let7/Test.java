import java.util.Scanner;

public class Test {
    private static final double G = 9.81;
    public static void main(String[] args) {
        double[] k = {1, 6, 11, 6};
        Polynom.of(k, 1e-5)
                .map(x-> new Point(x, Polynom.y(k, x)))
                .map(pt-> pt.dist())
                .forEach(x-> System.out.println(x));
    }
}
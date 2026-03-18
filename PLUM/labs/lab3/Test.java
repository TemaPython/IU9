package lab3;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Matrix mt1 = new Matrix(5);
        for (int i = 0; i < mt1.n; i++) {
            for (int j = 0; j < mt1.n; j++) {
                mt1.repl(i, j, i + j);
            }
        }

        int x = 0;
        Matrix mt2 = new Matrix(5);
        for (int i = 0; i < mt2.n; i++) {
            for (int j = 0; j < mt2.n; j++) {
                mt2.repl(i, j, x);
                x++;
            }
        }

        Matrix mt3 = new Matrix(4);
        for (int i = 0; i < mt3.n; i++) {
            for (int j = 0; j < mt3.n; j++) {
                mt3.repl(i, j, i - j);
            }
        }

        Matrix[] mats = new Matrix[] {mt1, mt2, mt3};
        System.out.println("Before sort");
        for (Matrix m : mats) {
            System.out.println(m);
        }
        Arrays.sort(mats);
        System.out.println("After sort");
        for (Matrix m : mats) {
            System.out.println(m);
        }
    }
}
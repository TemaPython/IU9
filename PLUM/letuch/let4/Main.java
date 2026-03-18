import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int n = 5;
        Universe universe = new Universe(n);
        System.out.println("Время до инициализации вселенной: " + universe.gtTime());
        for (int i = 0; i < n; i++) {
            universe.addPart(new Body(String.valueOf(i)));
            System.out.println("Время до инициализации частицы " + i
                    + " " + universe.parts[i].gtTime());
        }

        double[] meanV = universe.getMeanV();
        System.out.printf("Средний вектор направления: %.2f %.2f %.2f\n",
                meanV[0], meanV[1], meanV[2]);

        System.out.printf("Сила притяжения действующая на частицу %.2f Ньютонов",
                universe.getF(new Body("test", 5.0, -45.0, 34)));
    }
}

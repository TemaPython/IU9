import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(4, 5);
        List<Integer> c = Arrays.asList(6, 7, 10, 8);

        List[] arr = {a, b, c};
        Container<List<Integer>, Integer> p = new Container<>(arr);

        for (Object s : p) {
            System.out.println(s);
        }
    }
}
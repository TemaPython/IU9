import java.util.Arrays;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        int[] arr = {1134, -212, 4233};
        NumsDigits p = new NumsDigits(arr);

        for (int s : p) {
            System.out.println(s);
        }
    }
}

import javax.swing.text.html.Option;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

class IntsStream {
    ArrayList<BigInteger> Ints;
    IntsStream() {
        Ints = new ArrayList<>();
    }
    void add(BigInteger i) {
        Ints.add(i);
    }

    boolean isAllDig(String s) {
        String digits = "0123456789";
        for (char d : digits.toCharArray()) {
            if (s.indexOf(d) == -1) {
                return false;
            }
        }
        return true;
    }
    public Stream<BigInteger> nameStream() {
        return Ints.stream()
                .filter(x -> isAllDig(x.toString()));
    }
    public Optional<BigInteger> getInt() {
        return Ints.stream()
                .max(new SumComparator());
    }
}
class SumComparator implements Comparator<BigInteger> {
    int SumDigs(BigInteger s) {
        int sm = 0;
        for (char d : s.toString().toCharArray()) {
            sm += Character.getNumericValue(d);
        }
        return sm;
    }

    public int compare(BigInteger a, BigInteger b) {
        int a0 = SumDigs(a);
        int b0 = SumDigs(b);
        if (a0 > b0) { return 1; }
        if (a0 == b0) { return a.compareTo(b);}
        return -1;
    }
}
public class Test {
    public static void main(String[] args) {
        IntsStream t = new IntsStream();
        t.add(new BigInteger("4258178233903402576"));
        t.add(new BigInteger("4930125084"));
        t.add(new BigInteger("1234567890"));
        t.add(new BigInteger("9349200238577294423757"));
        t.nameStream().forEach(System.out::println);
        Map<Integer, List<BigInteger>> groupedByLength = t.Ints.stream()
                .collect(Collectors.groupingBy(x -> x.toString().length()));
        groupedByLength.forEach((len, list) -> {
            BigInteger maxInGroup = list.stream().max(new SumComparator()).get();
            System.out.println("длина " + len + " цифр: максимальное по сумме = " + maxInGroup);
        });

        System.out.println("общий максимум: " + t.getInt().orElse(BigInteger.ZERO));
    }
}
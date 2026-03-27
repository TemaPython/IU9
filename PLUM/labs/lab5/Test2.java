import java.util.Optional;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

class Matrix {
    public long matrix;
    public int m, n;
    Matrix(long matrix, int m, int n) {
        this.matrix = matrix;
        this.m = m;
        this.n = n;
    }
}

class MatrixStream {
    Matrix matrix;
    long mask;
    int total;
    MatrixStream(long num, int m, int n) {
        this.matrix = new Matrix(num, m, n);
        this.mask = (1L << matrix.n) - 1;
        this.total = Long.bitCount(matrix.matrix);
    }

    public Stream<Integer> MatStream() {
        return IntStream.range(0, matrix.m)
                .mapToObj(i -> Long.bitCount(matrix.matrix >> (i * matrix.n) & mask) % 2);
    }
    public Optional<Integer> getMaxOnes() {
        return IntStream.range(0, matrix.m)
                .filter(i -> 2 * Long.bitCount(matrix.matrix >> (i * matrix.n) & mask) > total)
                .boxed()
                .findFirst();
    }
}

public class Test2 {
    public static void main(String[] args) {
        long num = Long.parseLong("0000" + "0001" + "1111", 2);

        MatrixStream t = new MatrixStream(num, 3, 4);
        Map<Integer, Long> check = t.MatStream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println("нулевых сумм: " + check.getOrDefault(0, 0L));
        System.out.println("ненулевых сумм: " + check.getOrDefault(1, 0L));
        t.getMaxOnes().ifPresent(System.out::println);
    }
}
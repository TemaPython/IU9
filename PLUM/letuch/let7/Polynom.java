import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.lang.Math;

public class Polynom<T> {
    final static double RANGE = 1000.0;
    private HashSet<T> container;

    private Polynom(HashSet<T> container) {
        this.container = container;
    }

    public static double y(double[] k, double x) {
        double p = 0;
        int n = k.length;
        for (int j = 0; j < n; j++) {
            p += Math.pow(x, n-1-j) * k[j];
        }
        return p;
    }

    public static Polynom<Double> of(
            double[] k, double eps) {
        HashSet<Double> roots = new HashSet<>();
        for (double i = -RANGE; i < RANGE; i += 0.1) {
            if (Math.abs(y(k, i)) < eps) {
                roots.add(i);
            }
        }
        return new Polynom<>(roots);
    }

    public <R> Polynom<R> map(Function<T, R> f) {
        HashSet<R> c = new HashSet<>();
        for (T t: container) {c.add(f.apply(t)); }
        return new Polynom<>(c);
    }

    public void forEach(Consumer<T> f) {
        for (T t : container) {f.accept(t); }
    }
}

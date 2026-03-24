import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class Roots<T> {
    public ArrayList<T> container;

    public Roots(ArrayList<T> container) {
        this.container = container;
    }

    public static Roots<Double> of(
            double a, double b, double c, double eps) {
        ArrayList<Double> roots = new ArrayList<>();
        if (a == 0.0) {
            if (b != 0.0) roots.add(-c/b);
        } else {
            double d = b*b - 4*a*c;
            if (d >= 0.0) {
                if (d < eps) { d = 0.0;}
                roots.add((-b+Math.sqrt(d))/(2*a));
                roots.add((-b-Math.sqrt(d))/(2*a));
            }
        }
        return new Roots<>(roots);
    }

    public <R> Roots<R> map(Function<T, R> f) {
        ArrayList<R> c = new ArrayList<>();
        for (T t: container) {c.add(f.apply(t)); }
        return new Roots<>(c);
    }

    public void forEach(Consumer<T> f) {
        for (T t : container) {f.accept(t); }
    }
}

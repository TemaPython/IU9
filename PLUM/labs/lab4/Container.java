import java.util.Iterator;
import java.util.Arrays;

public class Container<T extends Iterable<E>, E> implements Iterable<E> {
    private T[] u;
    private int size;
    public Container(T[] u) {
        this.u = Arrays.copyOf(u, u.length * 2);
        this.size = u.length;
    }
    public void add(T container) {
        if (size == u.length) {
            u = Arrays.copyOf(u, u.length * 2);
        }
        u[size++] = container;
    }
    public void remove(int index) {
        System.arraycopy(u, index + 1, u, index, size - index - 1);
        u[--size] = null;
    }
    public void set(int index, T container) {
        u[index] = container;
    }
    public Iterator<E> iterator() {return new ContainerIterator();}

    private class ContainerIterator implements Iterator<E> {
        private int pos;
        public ContainerIterator() {pos = 0;}
        private Iterator<?> now = u[pos].iterator();
        public boolean hasNext() {
            if (now == null) return false;
            return pos + 1 < size || now.hasNext();
        }
        public E next() {
            while (!now.hasNext()) {
                if (hasNext()) {
                    now = u[++pos].iterator();
                } else {
                    break;
                }

            }
            return (E)now.next();
        }
    }

}
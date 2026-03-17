import java.util.Arrays;

public class Stack<T> {
    private int count = 0;
    private Object[] buf = new Object[16];

    public boolean empty() {
        return count == 0;
    }

    public int genLen() {return count;}

    public void push(T x) {
        if (count == buf.length) {
            buf = Arrays.copyOf(buf, buf.length * 2);
        }
        buf[count++] = x;
    }
    public T check() {
        if (empty()) {
            throw new RuntimeException("underflow");
        }
        return (T)buf[count-1];
    }

    public T pop() {
        if (empty()) {
            throw new RuntimeException("underflow");
        }
        return (T)buf[--count];
    }
}

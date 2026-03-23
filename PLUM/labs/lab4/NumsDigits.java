import java.util.Arrays;
import java.util.Iterator;

public class NumsDigits implements Iterable<Integer> {
    private int[] numList;
    private int size;

    public NumsDigits(int[] numList) {
        this.numList = Arrays.copyOf(numList, numList.length * 2);
        this.size = numList.length;}


    public void add(int n) {
        if (size == numList.length) {
            numList = Arrays.copyOf(numList, numList.length * 2);
        }
        numList[size++] = n;
    }
    public void remove(int index) {
        System.arraycopy(numList, index + 1, numList, index, size - index - 1);
        numList[--size] = 0;
    }
    public Iterator<Integer> iterator() {return new DigitsIterator();}

    public class DigitsIterator implements Iterator<Integer> {
        private int numPos = 0;
        private int digitPos = 0;
        private String current = String.valueOf(Math.abs(numList[0]));
        public boolean hasNext() {
            return numPos < size;
        }
        public Integer next() {
            int digit = current.charAt(digitPos) - '0';
            digitPos++;
            if (digitPos == current.length()) {
                numPos++;
                digitPos=0;
                if (numPos < size) {
                    current = String.valueOf(Math.abs(numList[numPos]));
                }
            }
            return digit;
        }
    }
}

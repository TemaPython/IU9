//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main1 {
    public static void main(String[] args) {
        Matrix mt1 = new Matrix(5);
        for (int i = 0; i < mt1.n; i++) {
            for (int j = 0; j < mt1.n; j++) {
                mt1.repl(i, j, i + j);
            }
        }

        int x = 0;
        Matrix mt2 = new Matrix(5);
        for (int i = 0; i < mt2.n; i++) {
            for (int j = 0; j < mt2.n; j++) {
                mt2.repl(i, j, x);
                x++;
            }
        }

        Matrix mt3 = new Matrix(4);
        for (int i = 0; i < mt3.n; i++) {
            for (int j = 0; j < mt3.n; j++) {
                mt3.repl(i, j, i - j);
            }
        }

        Stack<Matrix> s = new Stack<Matrix>();
        s.push(mt1);
        s.push(mt2);
        s.push(mt3);
        int sum = 0;
        while(!s.empty()) {
            Matrix r = s.pop();
            System.out.println(r);
            sum += r.tr();
        }
        System.out.println(sum);
    }
}
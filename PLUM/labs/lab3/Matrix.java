package lab3;

public class Matrix implements Comparable<Matrix>{
    public int n;
    private int[][] mat;

    public Matrix(int n) {
        this.n = n;
        mat = new int[n][n];
    }

    public void repl(int i, int j, int x) {
        mat[i][j] = x;
    }

    public int cntAntisim() {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                cnt += (mat[i][j] != mat[j][i]) ? 1 : 0;
            }
        }
        return cnt;
    }

    public int compareTo(Matrix obj) {
        int a_cnt = this.cntAntisim();
        int b_cnt = obj.cntAntisim();
        if (a_cnt > b_cnt) {
            return 1;
        } else if (a_cnt < b_cnt) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        int mx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mx = Math.max(mx, String.valueOf(mat[i][j]).length());
            }
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append("[ ");
            for (int j = 0; j < n; j++) {
                s.append(String.format("%" + (mx + 1) + "d ", mat[i][j]));
            }
            s.append("]\n");
        }
        return s.toString();
    }
}

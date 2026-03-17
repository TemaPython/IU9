public class Matrix{
    public int n;
    private int[][] mat;

    public Matrix(int n) {
        this.n = n;
        mat = new int[n][n];
    }

    public void repl(int i, int j, int x) {
        mat[i][j] = x;
    }

    public int tr() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += mat[i][i];
        }
        return sum;
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

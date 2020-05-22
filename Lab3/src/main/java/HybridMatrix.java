public class HybridMatrix {
    private double[][] m1;
    private double[][] m2;
    double t1;
    double t2;
    int n;

    public HybridMatrix(double[][] m1, double[][] m2, double t1, double t2, int n) {
        this.m1 = m1;
        this.m2 = m2;
        this.t1 = t1;
        this.t2 = t2;
        this.n = n;
    }

    public double[][] createHybrid() {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    matrix[i][j] = m1[i][j] * t1 + m2[i][j] * t2;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    matrix[i][j] = m2[i][j] * t2;
                }
            }
        }
        return matrix;
    }
}

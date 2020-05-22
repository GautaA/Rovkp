public class MatrixNormal {

    public double[][] createNormal(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            double max = Double.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                if (max < matrix[i][j]) max = matrix[i][j];
            }
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= max;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double avgValue = (matrix[i][j] + matrix[j][i]) / 2;
                matrix[i][j] = matrix[j][i] = avgValue;
            }
        }
        return matrix;
    }
}

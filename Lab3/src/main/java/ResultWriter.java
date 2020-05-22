import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {

    public void write(String path, double[][] matrix) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));

        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                bw.write((i + 1) + "," + (j + 1) + "," + matrix[i][j] + "\n");
            }
        }
        bw.close();
    }
}

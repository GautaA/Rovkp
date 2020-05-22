import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Recommender {
    public static void main(String[] args) throws IOException, ParseException {
        Map<Integer, String> jokes = new HashMap<>();

        JokeParser jp = new JokeParser("./dataset/jester_items.dat");
        jokes = jp.parse();

        Directory dir;
        StandardAnalyzer sa = new StandardAnalyzer();
        LucenDocs ld = new LucenDocs(jokes, sa);
        dir = ld.create();

        MatrixCreator mc = new MatrixCreator(sa, jokes, dir);
        double[][] matrix = mc.create();

        MatrixNormal mn = new MatrixNormal();
        double [][] newMatrix = mn.createNormal(matrix);

        ResultWriter rw = new ResultWriter();
        rw.write("./item_similarity.csv", newMatrix);

        int jokeId = find(newMatrix, 100);
        System.out.println("Najsličnija fora: " + jokeId);

    }

    public static int find(double[][] matrix, int id) {
        double maxValue = Double.MIN_VALUE;
        int n = matrix.length;
        int finalId = -1;
        id -= 1;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i == id || j == id) {
                    if (maxValue < matrix[i][j]) {
                        maxValue = matrix[i][j];
                        finalId = i != id ? i : j;
                    }
                }
            }
        }
        return finalId;
    }
}

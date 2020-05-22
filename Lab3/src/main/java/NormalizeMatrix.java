import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.util.List;

public class NormalizeMatrix {

    private ItemSimilarity cis;
    private int n;
    private List<Long> ids;

    public NormalizeMatrix(ItemSimilarity cis, int n, List<Long> ids) {
        this.cis = cis;
        this.n = n;
        this.ids = ids;
    }

    public double[][] createNormal() throws TasteException {

        double[][] matrix = new double[n][n];

        for (int i = 0; i < ids.size(); i++) {
            long id = ids.get(i);
            long[] ids = cis.allSimilarItemIDs(id);

            double sum = 0;
            for (int j = 0; j < ids.length; j++) {
                matrix[i][j] = cis.itemSimilarity(id, ids[j]);
                if (matrix [i][j] > 0) sum += cis.itemSimilarity(id, ids[j]);
            }
            for (int j = 0; j < ids.length; j++) {
                if (sum != 0) matrix[i][j] /= sum;
            }
        }
        return matrix;
    }
}

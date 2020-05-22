import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class Main {

    private static final int NUMBER_OF_ELEMENTS = 150;
    private static final int ID_RECOMMEND = 22;

    public static void main(String[] args) throws IOException, TasteException {
        DataModel model = new FileDataModel(
                new File("./dataset/jester_ratings.dat"), "\t\t");
        ItemSimilarity collaborativeItemSimilarity = new CollaborativeItemSimilarity(model);
        ItemSimilarity contentItemSimilarity = new FileItemSimilarity(new File("item_similarity.csv"));

        int n = model.getNumItems();
        LongPrimitiveIterator lpi = model.getItemIDs();
        List<Long> ids = new ArrayList<>();
        for (LongPrimitiveIterator it = lpi; it.hasNext(); ) {
            ids.add(it.next());
        }

        System.out.println(ids);

        NormalizeMatrix nm = new NormalizeMatrix(collaborativeItemSimilarity, n, ids);
        double[][] normalizedMatrix = nm.createNormal();

        List<Long> idsFile = new ArrayList<>();
        for (long i = 0; i < 150; i++) {
            idsFile.add(i);
        }

        NormalizeMatrix nmfile = new NormalizeMatrix(contentItemSimilarity, NUMBER_OF_ELEMENTS, idsFile);
        double [][] fileNormalizedMatrix = nmfile.createNormal();

        HybridMatrix hm = new HybridMatrix(normalizedMatrix, fileNormalizedMatrix, 0.5, 0.5, NUMBER_OF_ELEMENTS);
        double [][] hybridMatrix = hm.createHybrid();

        ResultWriter rw = new ResultWriter();
        rw.write("./hybrid_matrix.csv", hybridMatrix);

        HybridRecommender hr = new HybridRecommender(model, "./hybrid_matrix.csv", ID_RECOMMEND);
        hr.recommend();
    }
}
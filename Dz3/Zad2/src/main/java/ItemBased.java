import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

public class ItemBased {
    public static void main(String[] args) throws Exception {

        DataModel dm = new FileDataModel(
                new File("./dataset/jester_ratings.dat"), "\\s+");

        ItemSimilarity is = new FileItemSimilarity(
                new File("./item_similarity.csv"));


        ItemBasedRecommender ibr = new GenericItemBasedRecommender(dm, is);

        List<RecommendedItem> recommendations = ibr.recommend(22, 10);
        for (RecommendedItem ri : recommendations) {
            System.out.println(ri);
        }

        ItemRecommender ir = new ItemRecommender();
        double result = ir.generateResult(dm);
        System.out.println("Rezultat recommendera: " + result);
    }
}
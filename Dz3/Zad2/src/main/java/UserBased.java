import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBased {
    public static void main(String[] args) throws IOException, TasteException {
        DataModel dm = new FileDataModel(
                new File("./dataset/jester_ratings.dat"), "\\s+");

        UserSimilarity us = new PearsonCorrelationSimilarity(dm);

        UserNeighborhood un = new ThresholdUserNeighborhood(0.9, us, dm);

        UserBasedRecommender ubs = new GenericUserBasedRecommender(dm, un, us);

        List<RecommendedItem> recommendations = ubs.recommend(22, 10);
        for (RecommendedItem ri : recommendations) {
            System.out.println(ri);
        }

        UserRecommender ur = new UserRecommender();
        double result = ur.generateResult(dm);
        System.out.println("Rezultat recommendera: " + result);

    }
}
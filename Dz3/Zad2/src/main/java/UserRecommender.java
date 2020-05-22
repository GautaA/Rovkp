import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserRecommender {

    public double generateResult(DataModel dm) throws TasteException {
        RecommenderBuilder rb = model -> {
            UserSimilarity us = new PearsonCorrelationSimilarity(model);

            UserNeighborhood un = new ThresholdUserNeighborhood(0.9, us, model);

            return new GenericUserBasedRecommender(model, un, us);
        };

        RecommenderEvaluator re = new RMSRecommenderEvaluator();
        double result = re.evaluate(rb, null, dm, 0.3, 0.7);
        return result;
    }
}

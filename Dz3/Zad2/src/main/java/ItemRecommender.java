import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;

public class ItemRecommender {

    public double generateResult(DataModel dm) throws TasteException {
        RecommenderBuilder rb = model -> {
            ItemSimilarity is = new FileItemSimilarity(new File("./item_similarity.csv"));

            return new GenericItemBasedRecommender(model, is);
        };

        RecommenderEvaluator re = new RMSRecommenderEvaluator();
        double result = re.evaluate(rb, null, dm, 0.3, 0.7);
        return result;
    }
}

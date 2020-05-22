import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

public class HybridRecommender {
    private DataModel model;
    private String path;
    private int id;

    public HybridRecommender(DataModel model, String path, int id) {
        this.model = model;
        this.path = path;
        this.id = id;
    }

    public void recommend() throws TasteException {
        ItemSimilarity is = new FileItemSimilarity(new File(path));
        RecommenderBuilder rb = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dm) throws TasteException {


                return new GenericItemBasedRecommender(dm, is);
            }
        };
        ItemBasedRecommender ibr = new GenericItemBasedRecommender(model, is);

        List<RecommendedItem> recommendations = ibr.recommend(22, 10);
        for (RecommendedItem ri : recommendations) {
            System.out.println(ri);
        }

        RecommenderEvaluator re = new RMSRecommenderEvaluator();
        double result = re.evaluate(rb, null, model, 0.3, 0.7);
        System.out.println("Kvaliteta: " + result);
    }
}

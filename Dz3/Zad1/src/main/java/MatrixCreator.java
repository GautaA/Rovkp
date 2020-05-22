import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Map;

public class MatrixCreator {
    private StandardAnalyzer sa;
    private Map<Integer, String> jokes;
    private Directory dir;

    public MatrixCreator(StandardAnalyzer sa, Map<Integer, String> jokes, Directory dir) {
        this.sa = sa;
        this.jokes = jokes;
        this.dir = dir;
    }

    public double[][] create() throws IOException, ParseException {
        int n = jokes.size();
        double [][] matrix = new double[n][n];
        IndexReader ir = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(ir);

        for (int i = 1; i <= jokes.size(); i++) {
            Query query = new QueryParser("TEXT", sa).parse(QueryParser.escape(jokes.get(i)));
            TopDocs topDocs = is.search(query, n);
            ScoreDoc[] same = topDocs.scoreDocs;

            for (int j = 0; j < same.length; j++) {
                int k = Integer.parseInt(ir.document(same[j].doc).getField("ID").stringValue()) - 1;
                matrix[i - 1][k] = same[j].score;
            }
        }
        ir.close();
        return matrix;
    }
}

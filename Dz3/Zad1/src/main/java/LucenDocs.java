import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.Map;

public class LucenDocs {
    private Map<Integer, String> jokes;
    private Directory dir;
    private StandardAnalyzer sa;

    public LucenDocs(Map<Integer, String> jokes, StandardAnalyzer sa) {
        this.jokes = jokes;
        this.sa = sa;
    }

    public Directory create() throws IOException {
        dir = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(sa);
        IndexWriter iw = new IndexWriter(dir, config);

        for (int i = 1; i <= jokes.size(); i++) {
            FieldType idFieldType = new FieldType();
            idFieldType.setStored(true);
            idFieldType.setTokenized(false);
            idFieldType.setIndexOptions(IndexOptions.NONE);
            Field id = new Field("ID", String.valueOf(i), idFieldType);

            FieldType textFieldType = new FieldType();
            textFieldType.setStored(false);
            textFieldType.setTokenized(true);
            textFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            Field text = new Field("TEXT", jokes.get(i), textFieldType);


            Document doc = new Document();
            doc.add(id);
            doc.add(text);
            iw.addDocument(doc);
        }
        iw.close();

        return dir;
    }
}

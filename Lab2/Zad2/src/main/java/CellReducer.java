import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CellReducer extends Reducer<Text, Data, Text, Data> {
    @Override
    public void reduce(Text key, Iterable<Data> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        double totalAmonunt = 0;

        for (Data value : values) {
            totalAmonunt += value.getTotalMoneyEndedCell();
            count++;
        }

        context.write(key, new Data(count, totalAmonunt));
    }
}

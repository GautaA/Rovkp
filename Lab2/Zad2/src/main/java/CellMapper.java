import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CellMapper extends Mapper<LongWritable, Text, Text, Data> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        String line = value.toString();

        parser.parse(line);
        context.write(new Text(parser.getCordX() + "." + parser.getCordY()), new Data(0, parser.getTotalAmount()));
    }
}

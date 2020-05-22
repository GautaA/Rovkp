import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FilterMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        String line = value.toString();
        try {
            parser.parse(line);
            if (parser.filter()) {
                context.write(NullWritable.get(), new Text(parser.getWholeLine()));
            }
        } catch (Exception ex) {
            System.out.println("Cannot parse: " + line + "due to the " + ex);
        }
    }
}

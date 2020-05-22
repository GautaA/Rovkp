import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TotalTimeMapper extends Mapper<LongWritable, Text, Text, ShortestLongestDrive> {

    @Override
    public void map(LongWritable key, Text value, Context context) {

        DEBSRecordParser parser = new DEBSRecordParser();

        if (key.get() > 0) {
            String record = value.toString();
            try {
                parser.parse(record);
                context.write(new Text(parser.getMedallion()), new ShortestLongestDrive(parser.getTripTimeInSecs(),
                        parser.getTripTimeInSecs(), parser.getTripTimeInSecs()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

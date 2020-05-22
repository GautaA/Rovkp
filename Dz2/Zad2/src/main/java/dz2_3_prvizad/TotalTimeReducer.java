package dz2_3_prvizad;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TotalTimeReducer extends Reducer<Text, ShortestLongestDrive, Text, ShortestLongestDrive> {

    @Override
    public void reduce(Text key, Iterable<ShortestLongestDrive> values, Context context) throws IOException, InterruptedException {
        double totalTime = 0;
        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;

        for (ShortestLongestDrive value : values) {
            totalTime += value.getTotalTravel();

            if (value.getMinTravel() < minTime) {
                minTime = value.getMinTravel();
            }

            if (value.getMaxTravel() > maxTime) {
                maxTime = value.getMaxTravel();
            }
        }

        context.write(key, new ShortestLongestDrive(totalTime, minTime, maxTime));
    }
}

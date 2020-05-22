package dz2_3_prvizad;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Job job = Job.getInstance();
        job.setJarByClass(Driver.class);
        job.setJobName("Time driver");

        FileInputFormat.addInputPath(job, new Path("/user/rovkp/dz2/trip_data_small.csv"));
        FileOutputFormat.setOutputPath(job, new Path("/user/rovkp/dz2/results_dz2_zad1"));

        job.setMapperClass(TotalTimeMapper.class);
        job.setCombinerClass(TotalTimeReducer.class);
        job.setReducerClass(TotalTimeReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ShortestLongestDrive.class);

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }
}

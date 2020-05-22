package dz2_3;

import dz2_3_drugizad.PatternMapper;
import dz2_3_drugizad.PatternPartitioner;
import dz2_3_drugizad.PatternReducer;
import dz2_3_prvizad.ShortestLongestDrive;
import dz2_3_prvizad.TotalTimeMapper;
import dz2_3_prvizad.TotalTimeReducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job firstJob = Job.getInstance();
        firstJob.setJarByClass(Driver.class);
        firstJob.setJobName("Chain");

        FileInputFormat.addInputPath(firstJob, new Path("/user/rovkp/dz2/trip_data_small.csv"));
        FileOutputFormat.setOutputPath(firstJob, new Path("/user/rovkp/dz2/zad3particije"));

        firstJob.setMapperClass(PatternMapper.class);
        firstJob.setPartitionerClass(PatternPartitioner.class);
        firstJob.setReducerClass(PatternReducer.class);
        firstJob.setNumReduceTasks(6);

        firstJob.setOutputKeyClass(IntWritable.class);
        firstJob.setOutputValueClass(Text.class);

        boolean isFinished = firstJob.waitForCompletion(true);
        List<String> inputPaths = new ArrayList<>();
        List<String> outputPaths = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            inputPaths.add("/user/rovkp/dz2/zad3particije/part-r-0000" + i);
            outputPaths.add("/user/rovkp/dz2/zad3output_0" + i);
        }

        for (int i = 0; i < 6; i++) {
            Job secondJob = Job.getInstance();
            secondJob.setJarByClass(Driver.class);
            secondJob.setJobName("Second job");

            FileInputFormat.addInputPath(secondJob, new Path(inputPaths.get(i)));
            FileOutputFormat.setOutputPath(secondJob, new Path(outputPaths.get(i)));

            secondJob.setMapperClass(TotalTimeMapper.class);
            secondJob.setCombinerClass(TotalTimeReducer.class);
            secondJob.setReducerClass(TotalTimeReducer.class);

            secondJob.setOutputKeyClass(Text.class);
            secondJob.setOutputValueClass(ShortestLongestDrive.class);

            secondJob.waitForCompletion(true);
        }
    }
}

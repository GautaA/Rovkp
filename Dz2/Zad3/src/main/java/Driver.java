import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Job job = Job.getInstance();
        job.setJarByClass(Driver.class);
        job.setJobName("Parttitioner");

        FileInputFormat.addInputPath(job, new Path("/user/rovkp/dz2/trip_data_small.csv"));
        FileOutputFormat.setOutputPath(job, new Path("/user/rovkp/dz2/results_dz2_zad2"));

        job.setMapperClass(PatternMapper.class);
        job.setPartitionerClass(PatternPartitioner.class);
        job.setReducerClass(PatternReducer.class);
        job.setNumReduceTasks(6);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }
}

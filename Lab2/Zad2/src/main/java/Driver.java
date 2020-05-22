import org.apache.hadoop.fs.Path;
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

        FileInputFormat.addInputPath(job, new Path("/user/rovkp/agauta/zad1rez"));
        FileOutputFormat.setOutputPath(job, new Path("/user/rovkp/agauta/zad2rez"));

        job.setMapperClass(CellMapper.class);
        job.setReducerClass(CellReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Data.class);

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }
}

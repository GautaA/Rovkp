import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Serialization {

    private static final int numberOfReadings = 100000;

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration();
        Path path = new Path("/user/rovkp/agauta/ocitanja.bin");

        SequenceFile.Writer writer = SequenceFile.createWriter(config,
                SequenceFile.Writer.file(path),
                SequenceFile.Writer.keyClass(IntWritable.class),
                SequenceFile.Writer.valueClass(DoubleWritable.class));

        Random random = new Random();

        for (int i = 0; i < numberOfReadings; i++) {
            writer.append(new IntWritable( random.nextInt(100) + 1),
                    new DoubleWritable(99.99 * random.nextDouble()));
        }

        writer.close();

        SequenceFile.Reader reader = new SequenceFile.Reader(config, SequenceFile.Reader.file(path));
        
        IntWritable key = new IntWritable();
        DoubleWritable value = new DoubleWritable();

        List<Integer> count = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            count.add(i, 0);
            values.add(i, 0.0);
        }

        while (reader.next(key, value)) {
            count.set(key.get() - 1, count.get(key.get() - 1) + 1);
            values.set(key.get() - 1, values.get(key.get() - 1) + value.get());
        }

        reader.close();

        for (int i = 0; i < values.size(); i++) {
            System.out.printf("Senzor " + (i+1) + ": %f\n", ((values.get(i)) / count.get(i)));
        }
    }
}

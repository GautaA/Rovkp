package dz2_3_drugizad;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class PatternPartitioner extends Partitioner<IntWritable, Text> {

    @Override
    public int getPartition(IntWritable key, Text value, int numberOfPartitions) {

        DEBSRecordParser parser = new DEBSRecordParser();

        parser.parse(value.toString());


        switch (key.get()) {
            case 0: //outer city
                switch (getRealNumber(parser.getCustomerCount())) {
                    case 1:
                        return 0;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                    default:
                        return 6;
                }
            case 1: //inner city
                switch (getRealNumber(parser.getCustomerCount())) {
                    case 1:
                        return 3;
                    case 2:
                        return 4;
                    case 3:
                        return 5;
                    default:
                        return 6;
                }
            default:
                return 6;
        }
    }


    public int getRealNumber(int value) {
        if (value == 1) {
            return 1;
        } else if (value == 2 || value == 3) {
            return 2;
        } else {
            return 3;
        }
    }
}

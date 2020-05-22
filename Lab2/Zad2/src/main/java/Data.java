import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Data implements WritableComparable<Data> {

    private IntWritable countDrivesEndedCell;
    private DoubleWritable totalMoneyEndedCell;

    public Data() {
        this(0, 0.0);
    }

    public Data(int count, double total) {
        set(new IntWritable(count), new DoubleWritable(total));
    }

    public void set(IntWritable count, DoubleWritable total) {
        this.countDrivesEndedCell = count;
        this.totalMoneyEndedCell = total;
    }

    public int getCountDrivesEndedCell() {
        return countDrivesEndedCell.get();
    }

    public double getTotalMoneyEndedCell() {
        return totalMoneyEndedCell.get();
    }

    @Override
    public int compareTo(Data shortestLongestDrive) {
        int cmp = countDrivesEndedCell.compareTo(shortestLongestDrive.countDrivesEndedCell);
        if (cmp != 0) {
            return cmp;
        }

        return totalMoneyEndedCell.compareTo(shortestLongestDrive.totalMoneyEndedCell);
    }

    @Override
    public int hashCode() {
        return countDrivesEndedCell.hashCode() * 123 + totalMoneyEndedCell.hashCode();
    }

    public void write(DataOutput dataOutput) throws IOException {
        countDrivesEndedCell.write(dataOutput);
        totalMoneyEndedCell.write(dataOutput);
    }

    public void readFields(DataInput dataInput) throws IOException {
        countDrivesEndedCell.readFields(dataInput);
        countDrivesEndedCell.readFields(dataInput);
    }

    @Override
    public String toString() {
        return countDrivesEndedCell.toString() + "\t" + totalMoneyEndedCell.toString();
    }
}

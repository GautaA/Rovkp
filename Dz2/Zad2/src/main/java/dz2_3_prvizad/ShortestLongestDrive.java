package dz2_3_prvizad;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortestLongestDrive implements WritableComparable<ShortestLongestDrive> {

    private DoubleWritable totalTravel;
    private DoubleWritable minTravel;
    private DoubleWritable maxTravel;

    public ShortestLongestDrive() {
        this(0, Double.MAX_VALUE, Double.MIN_VALUE);
    }

    public ShortestLongestDrive(double tripTimeInSecs, double timeInSecs) {
        set(new DoubleWritable(), new DoubleWritable(), new DoubleWritable());
    }

    public ShortestLongestDrive(double first, double second, double third) {
        set(new DoubleWritable(first), new DoubleWritable(second), new DoubleWritable(third));
    }

    public void set(DoubleWritable first, DoubleWritable second, DoubleWritable third) {
        this.totalTravel = first;
        this.minTravel = second;
        this.maxTravel = third;
    }

    public double getTotalTravel() {
        return totalTravel.get();
    }

    public double getMinTravel() {
        return minTravel.get();
    }

    public double getMaxTravel() {
        return maxTravel.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  ShortestLongestDrive) {
            ShortestLongestDrive sld = (ShortestLongestDrive) o;
            return totalTravel.equals(sld.totalTravel) && minTravel.equals(sld.minTravel) && maxTravel.equals(sld.maxTravel);
        }
        return false;
    }

    @Override
    public int compareTo(ShortestLongestDrive shortestLongestDrive) {
        int cmp = totalTravel.compareTo(shortestLongestDrive.totalTravel);
        if (cmp != 0) {
            return cmp;
        }

        cmp = minTravel.compareTo(shortestLongestDrive.minTravel);
        if (cmp != 0) {
            return  cmp;
        }

        return maxTravel.compareTo(shortestLongestDrive.maxTravel);
    }

    @Override
    public int hashCode() {
        return totalTravel.hashCode() * 123 + minTravel.hashCode() + maxTravel.hashCode();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        totalTravel.write(dataOutput);
        minTravel.write(dataOutput);
        maxTravel.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        totalTravel.readFields(dataInput);
        minTravel.readFields(dataInput);
        maxTravel.readFields(dataInput);
    }

    @Override
    public String toString() {
        return totalTravel.toString() + "\t" + minTravel.toString() + "\t" + maxTravel.toString();
    }
}

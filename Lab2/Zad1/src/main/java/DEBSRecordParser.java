public class DEBSRecordParser {
    private static final double LAT_A = 41.474937;
    private static final double LONG_A = -74.913585;
    private static final double LAT_C = 40.1274702;
    private static final double LONG_C = -73.117785;

    private String wholeLine;
    private double totalAmount;
    private double startLat;
    private double startLong;
    private double endLat;
    private double endLong;

    public void parse(String line) {
        wholeLine = line;
        String[] splitted = line.split(",");

        totalAmount = Double.parseDouble(splitted[16]);
        startLat = Double.parseDouble(splitted[7]);
        startLong = Double.parseDouble(splitted[6]);
        endLat = Double.parseDouble(splitted[9]);
        endLong = Double.parseDouble(splitted[8]);
    }

    public String getWholeLine() {
        return wholeLine;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getStartLat() {
        return startLat;
    }

    public double getStartLong() {
        return startLong;
    }

    public double getEndLat() {
        return endLat;
    }

    public double getEndLong() {
        return endLong;
    }

    public boolean filter() {
        if (startLat <= LAT_A && startLat >= LAT_C
        && startLong >= LONG_A && startLong <= LONG_C
        && endLat <= LAT_A && endLat >= LAT_C
        && endLong >= LONG_A && endLong <= LONG_C
        && totalAmount > 0) {
            return true;
        }
        return false;
    }
}

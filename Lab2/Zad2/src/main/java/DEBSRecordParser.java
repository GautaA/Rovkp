public class DEBSRecordParser {

    private static final double LON_A = -74.913585;
    private static final double LAT_A = 41.474937;
    private static final double GRID_LENGTH = 0.011972;
    private static final double GRID_WIDTH = 0.008983112;

    private String wholeLine;
    private double totalAmount;
    private double startLon;
    private double startLat;
    private int cordX;
    private int cordY;


    public void parse(String line) {
        String[] splitRecord = line.split(",");

        totalAmount = Double.parseDouble(splitRecord[16]);
        startLon = Double.parseDouble(splitRecord[6]);
        startLat = Double.parseDouble(splitRecord[7]);

        cordX = calcLat(startLat);
        cordY = calcLon(startLon);
    }

    public int calcLat(Double startLat) {
        return (int)((LAT_A - startLat) / GRID_WIDTH);
    }

    public int calcLon(Double startLon) {
        return (int)((LON_A - startLon) / GRID_LENGTH);
    }

    public String getWholeLine() {
        return wholeLine;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }
}

package dz2_3_prvizad;

public class DEBSRecordParser {

    private String medallion;
    private double tripTimeInSecs;

    public void parse(String record) {
        String[] splitRecord = record.split(",");
        medallion = splitRecord[0];
        tripTimeInSecs = Double.parseDouble(splitRecord[8]);
    }

    public String getMedallion() {
        return medallion;
    }

    public double getTripTimeInSecs() {
        return tripTimeInSecs;
    }
}

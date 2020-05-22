import java.util.ArrayList;
import java.util.List;

public class DEBSRecordParser {
    private String wholeLine;
    private int customerCount;
    private List<Double> start;
    private List<Double> finish;

    public DEBSRecordParser() {
        start = new ArrayList<>();
        finish = new ArrayList<>();
    }

    public void parse(String line) {
        wholeLine = line;

        String[] splitted = line.split(",");
        customerCount = Integer.parseInt(splitted[7]);

        start.add(Double.parseDouble(splitted[10]));
        start.add(Double.parseDouble(splitted[11]));

        finish.add(Double.parseDouble(splitted[12]));
        finish.add(Double.parseDouble(splitted[13]));
    }

    public String getWholeLine() {
        return wholeLine;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public List<Double> getStart() {
        return start;
    }

    public List<Double> getFinish() {
        return finish;
    }

    public int isInnerCity() {
        if (start.get(0) >= -74 && start.get(0) <= -73.95
        && finish.get(0) >= -74 && finish.get(0) <= -73.95
        && start.get(1) <= 40.8 && start.get(1) >= 40.75
        && finish.get(1) <= 40.8 && finish.get(1) >= 40.75) {
            return 1;
        }
        return 0;
    }

}

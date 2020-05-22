import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JokeParser {
    private Map<Integer, String> jokes;
    private String filePath;
    private BufferedReader br;
    private int lastId;

    public JokeParser(String filePath) {
        this.jokes = new HashMap<>();
        this.filePath = filePath;
    }

    public Map parse() throws IOException {
        br = new BufferedReader(new FileReader(filePath));
        String current;
        while ((current = br.readLine()) != null) {
            String cleanCurrent = StringEscapeUtils.unescapeXml(current.toLowerCase().replaceAll("\\<.*?\\>", ""));
            if (isId(cleanCurrent)) {
                jokes.put(Integer.parseInt(cleanCurrent.replace(":", "")), "");
            } else {
                if (jokes.get(lastId).equals("")) jokes.replace(lastId, cleanCurrent + "\n");
                else jokes.replace(lastId, jokes.get(lastId) + cleanCurrent + "\n");
            }
        }
        return jokes;
    }

    public boolean isId(String line) {
        String removeLast = line.replaceFirst(".$","");
        if (NumberUtils.isCreatable(removeLast)) {
            lastId = Integer.parseInt(removeLast);
            return true;
        }
        return false;
    }
}

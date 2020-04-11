import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayDeque;

public class MergeCollections {
    public static void main(String[] args) throws IOException, URISyntaxException {
        long startTime = System.currentTimeMillis();

        Configuration config = new Configuration();

        LocalFileSystem lfs = LocalFileSystem.getLocal(config);
        FileSystem fs = FileSystem.get(config);

        Path localPath = new Path("/home/rovkp/agauta/gutenberg");
        Path hdfsPath = new Path("/user/rovkp/agauta/gutenberg_books.txt");

        ArrayDeque<File> files = new ArrayDeque<File>();
        files.add(new File(String.valueOf(localPath)));

        int countFiles = 0;
        int countLines = 0;

        BufferedReader reader;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fs.create(hdfsPath)));

        while (!files.isEmpty()) {
            File[] filesArray = files.poll().listFiles();
            for (File f : filesArray) {
                if (f.isDirectory()) {
                    files.add(f);
                } else {
                    if (f.getName().endsWith(".txt"))  {
                        countFiles++;
                        reader = new BufferedReader(new FileReader(f));
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            countLines++;
                            writer.write(currentLine);
                            writer.flush();
                        }
                        reader.close();
                    }
                }
            }
        }

        writer.close();
        lfs.close();
        fs.close();

        System.out.println("Ukupno je pročitano " + countFiles + " tekstualnih datoteka.");
        System.out.println("Ukupno je pročitano " + countLines + " redaka.");
        System.out.println("Ukupno vrijeme izvršavanja programa je " + (System.currentTimeMillis() - startTime) + " ms.");

    }
}

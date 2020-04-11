import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ConfigMain {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration config = new Configuration();
        LocalFileSystem lfs = LocalFileSystem.getLocal(config);
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), config);

        Path localPath = new Path("/home/rovkp/ROVKP_DZ1/");
        Path hdfsPath = new Path("/user/rovkp/gutenberg.zip");

        System.out.println("Lokalni file status: \n" + lfs.getFileStatus(localPath));
        System.out.println("Hdfs file status: \n" + fs.getFileStatus(hdfsPath));

        fs.close();
        lfs.close();

    }
}

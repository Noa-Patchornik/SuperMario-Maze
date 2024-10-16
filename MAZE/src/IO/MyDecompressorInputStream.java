package IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    public MyDecompressorInputStream(FileInputStream fileInputStream) {
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}

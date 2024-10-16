//package IO;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SimpleCompressorOutputStream extends OutputStream {
//
//    private OutputStream out;
//
//    public SimpleCompressorOutputStream(OutputStream out) {
//        this.out = out;
//    }
//
//    @Override
//    public void write(int b) throws IOException {
//        out.write(b);
//    }
//
//    @Override
//    public void write(byte[] b) throws IOException {
//        // Write first 24 bytes directly (for metadata like start, end, rows, cols)
//        for (int i = 0; i < 24; i++) {
//            write(b[i]);
//        }
//
//        int i = 24;
//        int currentByte = b[i];
//        int count = 0;
//        List<Byte> compressed = new ArrayList<>();
//        if(b[24]==1){
//            compressed.add((byte) 0);
//        }
//        while (i < b.length) {
//            if (b[i] == currentByte) {
//                count++;
//                if (count == 255) {  // Max sequence length for one byte
//                    compressed.add((byte) count);
//                    count = 0;
//                }
//            } else {
//                compressed.add((byte) count);
//                currentByte = b[i];
//                count = 1;
//            }
//            i++;
//        }
//
//        // Write the last count
//        compressed.add((byte) count);
//
//        // Write to output
//        for (byte value : compressed) {
//            write(value);
//        }
//    }
//
//}


//----------------------------------------------YAKI------------------------------------------------------------------//

package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleCompressorOutputStream extends OutputStream {
    private final OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    public byte[] compress(byte[] arr) throws IOException {
        for(int t=0; t<24; t++){
            write(arr[t]);
        }
        int i = 24;
        List<Byte> ans = new ArrayList<>();
        if (arr[i] == 1) {
            ans.add((byte)-128);
        }
        int counter0 = 0;
        int counter1 = 0;
        while (i < arr.length) {
            if (arr[i] == 1) {
                counter1++;
                if (counter0 > 0) {
                    writeCounter(ans, counter0);
                    counter0 = 0;
                }
            } else {
                counter0++;
                if (counter1 > 0) {
                    writeCounter(ans, counter1);
                    counter1 = 0;
                }
            }
            i++;
        }

        // Add the last counted sequence
        if (counter0 > 0) {
            writeCounter(ans, counter0);
        }
        if (counter1 > 0) {
            writeCounter(ans, counter1);
        }

        // Convert list to byte array
        byte[] compressed = new byte[ans.size()];
        for (int j = 0; j < ans.size(); j++) {
            compressed[j] = ans.get(j);
        }

        for (byte b : compressed) {
            write(b);
        }
        return compressed;
    }

    private void writeCounter(List<Byte> ans, int counter) {
        while (counter > 255) {
            ans.add((byte) 127);
            ans.add((byte)-128);
            counter =counter- 255;
        }
        counter =counter- 128;
        ans.add((byte) counter);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] arr) throws IOException {
        compress(arr);
    }
}


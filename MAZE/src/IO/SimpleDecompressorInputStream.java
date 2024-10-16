
//package IO;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SimpleDecompressorInputStream extends InputStream {
//
//    private InputStream in;
//
//    public SimpleDecompressorInputStream(InputStream in) {
//        this.in = in;
//    }
//
//    @Override
//    public int read() throws IOException {
//        return 0;
//    }
//
//    @Override
//    public int read(byte[] b) throws IOException {
//        // Read all bytes from input stream
//        byte[] compressed = in.readAllBytes();
//        List<Byte> decompressed = new ArrayList<>();
//
//        // Copy the first 24 bytes (metadata)
//        for (int i = 0; i < 24; i++) {
//            decompressed.add(compressed[i]);
//        }
//
//        // Start decompressing the maze content
//        byte currentValue = 0; // Start with 0 (path)
//        for (int i = 24; i < compressed.length; i++) {
//            int count = Byte.toUnsignedInt(compressed[i]);
//
//            // Add the `count` number of currentValue (0 or 1)
//            for (int j = 0; j < count; j++) {
//                decompressed.add(currentValue);
//            }
//
//            // Toggle between 0 and 1 after each run
//            currentValue = (byte) (currentValue == 0 ? 1 : 0);
//        }
//
//        // Copy decompressed data into the output byte array
//        for (int i = 0; i < decompressed.size(); i++) {
//            b[i] = decompressed.get(i);
//        }
//
//        return decompressed.size();
//    }
//}

//-----------------------------------------------YAKI-----------------------------------------------------------------//
package IO;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream i){
        this.in=i;
    }

    public int Decompress(byte [] b) throws IOException {
        byte[] arr = in.readAllBytes();
        List<Byte> ans= new ArrayList<>();
        for(int t=0; t<24; t++){
            ans.add(arr[t]);
        }
        for(int i=24;i<arr.length;i++){
            if(i%2==0) {
                for (int j = 0; j < arr[i]+128; j++) {
                    ans.add((byte) 0);
                }
            }
            else{
                for (int j = 0; j < arr[i]+128; j++) {
                    ans.add((byte) 1);
                }
            }

        }
        for(int index=0;index<ans.size();index++){
            b[index]=ans.get(index);
        }
        return 0;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        Decompress(b);
        return 0;
    }

}

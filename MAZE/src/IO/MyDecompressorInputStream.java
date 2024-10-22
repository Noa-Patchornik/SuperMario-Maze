package IO;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream i) {
        this.in = i;
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

    public int Decompress(byte[] b) throws IOException {
        byte[] arr = in.readAllBytes();
        int index = 0;

//        the raw data is the same, no decompress is needed
        for (int t = 0; t < 24; t++) {
            b[index] = arr[t];
            index++;
        }


        for (int i = 24; i < arr.length; i++) {
            String binary = dec2Bin(arr[i]);
            int bits = Math.min(binary.length(), b.length - index);
            //puts elements in the maze according to the binary number
            for (int j = 0; j < bits; j++) {
                if(binary.charAt(j) == '1')
                    b[index] = (byte) 1;
                else{
                    b[index] = (byte) 0;
                }
                index++;
            }
            if (index >= b.length) {
                break;
            }
        }
        return index;
    }

    //get a byte value that represnt a decimal number and return its binary representation to save in the maze
    private String dec2Bin(byte value) {

        StringBuilder result = new StringBuilder();
        List<Integer> ans = new ArrayList<>();
        int val = 0;
        if (value < 0) {
            val = value + 256;
        } else {
            val = value;
        }
        while (val > 0) {
            int remain = val % 2;
            ans.add(0, remain);
            val = val / 2;
        }

        for (int i = 0; i < ans.size(); i++) {
            result.append(ans.get(i));
        }

        while (result.length() < 8) {
            result.insert(0, "0");
        }

        return result.toString();
    }
}

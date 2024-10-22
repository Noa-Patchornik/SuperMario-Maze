package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;
    public MyCompressorOutputStream(OutputStream o){
        this.out=o;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] arr) throws IOException {
        compress(arr);
    }

    public void compress(byte[] arr) throws IOException {

        //keep the raw data of the maze as it
        for (int t = 0; t < 24; t++) {
            write(arr[t]);
        }
        int i = 24;
        while (i < arr.length) {
            int decimal = 0;
            //makes a number for compressing
            int bits = Math.min(8, arr.length - i);
            for (int o = 0; o < 8; o++) {
                //just if element is 1 needs to ^2
                if (o < bits && arr[i + o] == 1) {
                    decimal += Math.pow(2, 7 - o);
                }
            }
            write(decimal);
            i += bits;
        }
    }

}



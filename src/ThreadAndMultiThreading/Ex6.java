package ThreadAndMultiThreading;

import java.io.*;

public class Ex6 {
    public static void writeStuff(OutputStream rawOut) {
        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(rawOut));

            int[] data = {82, 105, 99, 104, 97, 114, 100, 32, 72, 121, 100, 101};

            for (int datum : data) {
                out.writeInt(datum);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readStuff(InputStream rawIn) {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(rawIn));
            boolean eof = false;
            while (!eof) {
                try {
                    int i = in.readInt();
                    System.out.println("Vua doc xong: " + i);
                } catch (EOFException e) {
                    eof = true;
                    e.printStackTrace();
                }
            }
            System.out.println("Doc tat ca du lieu tu Pipe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            final PipedOutputStream out = new PipedOutputStream();

            final PipedInputStream in = new PipedInputStream(out);

            Runnable runA = () -> writeStuff(out);

            Thread threadA = new Thread(runA, "threadA");
            threadA.start();

            Runnable runB = () -> readStuff(in);

            Thread threadB = new Thread(runB, "threadB");
            threadB.start();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}

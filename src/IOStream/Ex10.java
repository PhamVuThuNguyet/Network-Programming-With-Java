package IOStream;

import java.io.*;

public class Ex10 {
    public static void main(String[] args) throws IOException {
        double[] arr = {1, 2, 3, 4, 5, 6};

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\test.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\test.txt", "r");
        for (double v : arr) {
            dataOutputStream.writeDouble(v);
        }

        try{
            for (int i = 0; i < randomAccessFile.length(); i++) {
                randomAccessFile.seek(8L * i);
                double d = randomAccessFile.readDouble();
                System.out.println(d);
            }
        }catch (EOFException e){
            System.out.println("Finish");
        }

        fileOutputStream.close();
        dataOutputStream.close();
        randomAccessFile.close();
    }
}

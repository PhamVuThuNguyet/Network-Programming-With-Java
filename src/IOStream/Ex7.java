package IOStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ex7 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\test.txt");
        int c;
        while((c = fileInputStream.read()) != -1){
            System.out.print((char)(c));
        }
        fileInputStream.close();
    }
}

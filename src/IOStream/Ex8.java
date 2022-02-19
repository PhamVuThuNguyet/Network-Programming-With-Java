package IOStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ex8 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\test.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\new_test.txt");
        int c;
        while((c = fileInputStream.read()) != -1){
            fileOutputStream.write(c);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}

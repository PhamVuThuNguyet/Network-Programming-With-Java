package IOStream;

import java.io.IOException;

public class Ex1 {
    public static void main(String[] args) throws IOException {
        byte[] data = new byte[5];
        System.out.println("Enter a byte array");
        System.in.read(data);
        System.out.println("Your byte array is...");
        for (byte datum : data) {
            System.out.print((char) datum);
        }
    }
}

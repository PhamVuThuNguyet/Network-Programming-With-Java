package IOStream;

import java.io.PrintWriter;

public class Ex6 {
    public static void main(String[] args) {
        String s = "Hello";
        PrintWriter printWriter = new PrintWriter(System.out, true);
        printWriter.println(s);
    }
}

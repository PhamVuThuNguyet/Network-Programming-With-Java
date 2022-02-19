package IOStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Ex2 {
    public static void main(String[] args) throws IOException {
        String a = "X";
        System.out.write(a.getBytes(StandardCharsets.UTF_8));
    }
}

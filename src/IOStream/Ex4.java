package IOStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex4 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string: ");

        String line;
        boolean stop = false;
        while (((line = bufferedReader.readLine()) != null) && (!stop)) {
            String[] words = line.split(" ");
            for (String word : words) {
                System.out.print(word + " ");
                if (word.equals("stop")) {
                    stop = true;
                    break;
                }
            }
        }
    }
}

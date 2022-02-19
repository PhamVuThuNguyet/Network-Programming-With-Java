package IOStream;

import java.io.*;

public class Ex11 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        FileWriter fileWriter = new FileWriter("D:\\test.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        System.out.println("Enter a string: ");

        String line;
        boolean stop = false;
        while (!stop) {
            line = bufferedReader.readLine();
            String[] words = line.split(" ");
            for (String word : words) {
                printWriter.print(word + " ");
                printWriter.flush();
                if (word.equals("stop")) {
                    stop = true;
                    break;
                }
            }
            printWriter.println();
        }
    }
}

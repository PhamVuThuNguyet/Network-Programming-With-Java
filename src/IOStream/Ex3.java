package IOStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex3 {
    public static void main(String[] args) throws IOException {
        char c;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println("Input a string end by dot: ");
        do{
            c = (char) bufferedReader.read();
            System.out.print(c);
        }while (c != '.');
    }
}

package SocketAndMultiSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ex3 {
    public static void main(String[] args) {
        String host = "localhost";

        if (args.length > 0) {
            host = args[0];
        }
        for (int i = 0; i < 1024; i++) {
            System.out.println("Looking for port " + i);
            try{
                new Socket(host, i);
                System.out.println("Day la mot server tren port " + i + " cua " + host);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

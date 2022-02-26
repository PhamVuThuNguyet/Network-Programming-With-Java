package SocketAndMultiSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Ex1 {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("www.dtu.edu.vn");
        int port = 80;
        Socket socket = new Socket(inetAddress, port);
        System.out.println(socket.getInetAddress());
    }
}

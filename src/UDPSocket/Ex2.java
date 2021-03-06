package UDPSocket;

import java.net.*;

public class Ex2 {
    public static void main(String[] args) {

        for (int port = 1024; port <= 65535; port++) {
            try {
                // the next line will fail and drop into the catch block if
                // there is already a server running on port i
                DatagramSocket server = new DatagramSocket(port);
                server.close();
            } catch (SocketException e) {
                System.out.println("There is a server on port " + port + ".");
            }
        }
    }
}


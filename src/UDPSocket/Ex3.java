
package UDPSocket;

import java.net.*;
import java.io.*;

public class Ex3 {

    public final static int DEFAULT_PORT = 9;

    public static void main(String[] args) {

        String hostname;
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            hostname = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            hostname = "localhost";
        }

        try {
            InetAddress server = InetAddress.getByName(hostname);
            BufferedReader userInput
                    = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket theSocket = new DatagramSocket();
            while (true) {
                String theLine = userInput.readLine();
                if (theLine.equals(".")) break;
                byte[] data = theLine.getBytes();
                DatagramPacket theOutput
                        = new DatagramPacket(data, data.length, server, port);
                theSocket.send(theOutput);
            }
        } catch (IOException sex) {
            sex.printStackTrace();
        }
    }
}

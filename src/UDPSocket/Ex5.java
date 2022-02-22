
package UDPSocket;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Ex5 extends Ex6 {

    public final static int DEFAULT_PORT = 2002;

    public Ex5() throws SocketException {
        super(DEFAULT_PORT);
    }

    public void respond(DatagramPacket packet) throws IOException {
        Date now = new Date();
        String response = now + "\r\n";
        byte[] data = response.getBytes(StandardCharsets.US_ASCII);
        DatagramPacket outgoing = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
        System.out.println(response);
        ds.send(outgoing);
        System.out.println("Sending...");
    }

    public static void main(String[] args) {

        try {
            Ex6 server = new Ex5();
            server.start();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

    }

}

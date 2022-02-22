package UDPSocket;

import java.net.*;
import java.io.*;

public class Ex9 extends Ex6 {

    public final static int DEFAULT_PORT = 7;

    public Ex9() throws SocketException {
        super(DEFAULT_PORT);
    }

    public void respond(DatagramPacket packet) throws IOException {

        DatagramPacket outgoing = new DatagramPacket(packet.getData(), packet.getLength(), packet.getAddress(), packet.getPort());
        ds.send(outgoing);
    }

    public static void main(String[] args) {
        try {
            Ex6 server = new Ex9();
            server.start();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
}


package UDPSocket;

import java.net.DatagramPacket;
import java.net.SocketException;

public class Ex7 extends Ex6 {

    public final static int DEFAULT_PORT = 2002;

    public Ex7() throws SocketException {
        super(DEFAULT_PORT);
    }

    public void respond(DatagramPacket packet) {
        String s = new String(packet.getData(), 0, packet.getLength());
        System.out.println(packet.getAddress() + " at port "
                + packet.getPort() + " says " + s);
    }

    public static void main(String[] args) {

        try {
            Ex6 server = new Ex7();
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}


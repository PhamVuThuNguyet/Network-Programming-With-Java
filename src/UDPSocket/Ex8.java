
package UDPSocket;

import java.net.DatagramPacket;
import java.net.SocketException;

public class Ex8 extends Ex6 {

    public final static int DEFAULT_PORT = 2002;

    public Ex8() throws SocketException {
        super(DEFAULT_PORT);
    }

    public void respond(DatagramPacket packet) {
        byte[] data = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());
        try {
            String s = new String(data, "8859_1");
            System.out.println(packet.getAddress() + " at port "
                    + packet.getPort() + " says " + s);
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            Ex6 server = new Ex8();
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

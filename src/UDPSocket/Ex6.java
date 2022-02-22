
package UDPSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public abstract class Ex6 extends Thread {

    private final int bufferSize; // in bytes
    protected DatagramSocket ds;

    public Ex6(int port, int bufferSize)
            throws SocketException {
        this.bufferSize = bufferSize;
        this.ds = new DatagramSocket(port);
    }

    public Ex6(int port) throws SocketException {
        this(port, 8192);
    }

    public void run() {

        byte[] buffer = new byte[bufferSize];
        while (true) {
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            try {
                ds.receive(incoming);
                this.respond(incoming);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public abstract void respond(DatagramPacket request) throws IOException;
}

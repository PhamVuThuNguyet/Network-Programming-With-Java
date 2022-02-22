
package UDPSocket;

import java.net.*;
import java.io.*;

public class Ex11 extends Thread {

    private final InetAddress server;
    private final DatagramSocket socket;
    private boolean stopped = false;
    private final int port;

    public Ex11(InetAddress address, int port) throws SocketException {
        this.server = address;
        this.port = port;
        this.socket = new DatagramSocket();
        this.socket.connect(server, port);
    }

    public void halt() {
        this.stopped = true;
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void run() {
        try {
            BufferedReader userInput
                    = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (stopped) return;
                String theLine = userInput.readLine();
                if (theLine.equals(".")) break;
                byte[] data = theLine.getBytes();
                DatagramPacket output
                        = new DatagramPacket(data, data.length, server, port);
                socket.send(output);
                Thread.yield();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

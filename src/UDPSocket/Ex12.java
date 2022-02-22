
package UDPSocket;

import java.net.*;
import java.io.*;

class Ex12 extends Thread {
    DatagramSocket socket;
    private boolean stopped = false;

    public Ex12(DatagramSocket ds) throws SocketException {
        this.socket = ds;
    }

    public void halt() {
        this.stopped = true;
    }

    public void run() {
        byte[] buffer = new byte[65507];
        while (true) {
            if (stopped) return;
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(dp);
                String s = new String(dp.getData(), 0, dp.getLength());
                System.out.println(s);
                Thread.yield();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

package TCPSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Ex1 {
    public static void main(String[] args) {
        for (String arg : args) {
            try {
                Socket theSocket = new Socket(arg, 80);
                System.out.println("Connected to " + theSocket.getInetAddress()
                        + " on port " + theSocket.getPort() + " from port "
                        + theSocket.getLocalPort() + " of " + theSocket.getLocalAddress());
            }
            catch (UnknownHostException e) {
                System.err.println("I can't find " + arg);
            } catch (SocketException e) {
                System.err.println("Could not connect to " + arg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}


package UDPSocket;

import java.net.*;

public class Ex10 {

    public final static int DEFAULT_PORT = 2002;

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }

        try {
            InetAddress ia = InetAddress.getByName(hostname);
            Ex11 sender = new Ex11(ia, port);
            sender.start();
            Thread receiver = new Ex12(sender.getSocket());
            receiver.start();
        } catch (UnknownHostException | SocketException ex) {
            ex.printStackTrace();
        }
    }
}


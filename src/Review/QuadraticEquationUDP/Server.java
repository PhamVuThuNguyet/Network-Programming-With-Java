package Review.QuadraticEquationUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Server {
    private final int port;
    public final static byte[] BUFFER = new byte[40960];

    public Server(int port) {
        this.port = port;
    }

    private void execute() throws IOException {
        DatagramSocket ds = null;
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            ds = new DatagramSocket(port);
            System.out.println("Server started ");
            System.out.println("Waiting for messages from Client ... ");

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                String message = new String(incoming.getData(), 0, incoming.getLength());
                System.out.println("Received: " + message);

                String[] arr = getData(message);
                String res = calculateRoots(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));

                DatagramPacket out = new DatagramPacket(res.getBytes(), incoming.getLength(),
                        incoming.getAddress(), incoming.getPort());
                ds.send(out);

                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        System.out.println("DISCONNECTED CLIENT");
    }

    private String[] getData(String s) {
        return s.trim().split(";");
    }

    private String calculateRoots(int a, int b, int c) {
        if (a == 0) {
            System.out.println("The value of a cannot be 0.");
            return "FAIL";
        }
        int d = b * b - 4 * a * c;
        double sqrtVal = sqrt(abs(d));
        if (d > 0) {
            return (-b + sqrtVal) / (2 * a) + "\n" + (-b - sqrtVal) / (2 * a);
        } else if (d == 0) {
            return (-(double) b / (2 * a) + "\n" + -(double) b / (2 * a));
        } else {
            return (-(double) b / (2 * a) + " + i" + sqrtVal + "\n" + -(double) b / (2 * a) + " - i" + sqrtVal);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(2022);
        server.execute();
    }
}

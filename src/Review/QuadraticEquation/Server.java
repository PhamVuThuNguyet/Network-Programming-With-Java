package Review.QuadraticEquation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    private void execute() throws IOException {
        System.out.println("Server is listening...");

        //Server config
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        String s = dataInputStream.readUTF();
        System.out.println(s);
        String[] arr = getData(s);
        String res = calculateRoots(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
        dataOutputStream.writeUTF(res);
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

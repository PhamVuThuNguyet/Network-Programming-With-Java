package Review.PerfectNum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<Integer> integers = checkData(arr);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }

    private String[] getData(String s) {
        return s.trim().split(";");
    }

    private ArrayList<Integer> checkData(String[] s) {
        ArrayList<Integer> ints = new ArrayList<>();
        for (String value : s) {
            System.out.println(value);
            if (isPerfect(Integer.parseInt(value))) {
                ints.add(Arrays.asList(s).indexOf(value));
            }
        }
        return ints;
    }

    private boolean isPerfect(int n) {
        if (n == 1)
            return false;

        int sum = 1;

        for (int i = 2; i * i <= n; i++) {

            if (n % i == 0) {
                if (i * i == n)
                    sum += i;
                else
                    sum += i + (n / i);
            }
        }

        return sum == n;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(2022);
        server.execute();
    }
}

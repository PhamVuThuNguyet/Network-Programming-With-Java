package Review.PerfectNum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final InetAddress host;
    private final int port;

    private final Scanner scanner = new Scanner(System.in);

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    private void execute() throws IOException {
        Socket client = new Socket(host, port);
        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());

        // Enter data
        String s = enterData();
        dataOutputStream.writeUTF(s);
        System.out.println(s);
    }

    private String enterData() {
        String s = "";
        while (true) {
            String temp = scanner.nextLine();
            if (temp.equals("stop"))
                break;
            s += temp;
        }
        return s;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(InetAddress.getByName("localhost"), 2022);
        client.execute();
    }
}

package Review.Login;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private final InetAddress host;
    private final int port;

    private final Scanner scanner = new Scanner(System.in);
    public final static byte[] BUFFER = new byte[40960];

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    private void execute() throws IOException {
        try (DatagramSocket client = new DatagramSocket()) {
            System.out.println("Client started ");
            while (true) {
                int count_ = 0;
                do {
                    //enter data
                    ArrayList<String> temp = enterData();
                    String userName = temp.get(0);
                    String password = temp.get(1);
                    count_++;

                    byte[] data = (userName + ";" + password + ";" + count_).getBytes();
                    DatagramPacket dp = new DatagramPacket(data, data.length, host, port);
                    client.send(dp);

                    DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                    client.receive(incoming);
                    System.out.println(new String(incoming.getData(), 0, incoming.getLength()));
                } while (count_ < 3);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private ArrayList<String> enterData() {
        String userName, password;

        userName = scanner.nextLine();
        password = scanner.nextLine();

        ArrayList<String> strings = new ArrayList<>();
        strings.add(userName);
        strings.add(password);

        return strings;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(InetAddress.getByName("localhost"), 2022);
        client.execute();
    }
}

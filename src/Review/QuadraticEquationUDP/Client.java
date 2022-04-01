package Review.QuadraticEquationUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
                // Enter data
                String s = enterData();
                byte[] data = s.getBytes();

                DatagramPacket dp = new DatagramPacket(data, data.length, host, port);
                client.send(dp);

                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                client.receive(incoming);
                System.out.println(new String(incoming.getData(), 0, incoming.getLength()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

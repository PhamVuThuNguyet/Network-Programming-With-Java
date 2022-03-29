package Review.PerfectNum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
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

        int count_ = count;
        do {
            //enter data
            ArrayList<String> temp = enterData();
            String userName = temp.get(0);
            String password = temp.get(1);
            count_++;

            dataOutputStream.writeUTF(userName);
            dataOutputStream.writeUTF(password);
            dataOutputStream.writeInt(count_);

            System.out.println(dataInputStream.readUTF());
        } while (count_ < 3);
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
        client.execute(0);
    }
}

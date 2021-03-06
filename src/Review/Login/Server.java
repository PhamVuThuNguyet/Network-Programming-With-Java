package Review.Login;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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

                String userName = message.split(";")[0];
                String password = message.split(";")[1];

                int count = Integer.parseInt(message.split(";")[2]);

                do {
                    //Check data
                    if (checkData(userName, password)) {
                        message = "Login Successfully";
                    } else {
                        if (count < 3) {
                            message = "Username or Password incorrect";
                        } else {
                            message = "Incorrect 3 times";
                            ds.close();
                        }
                    }
                } while (ds.isConnected());

                DatagramPacket out = new DatagramPacket(message.getBytes(), incoming.getLength(),
                        incoming.getAddress(), incoming.getPort());
                ds.send(out);

                System.out.println(message);
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

    private boolean checkData(String userName, String password) {
        return userName.equals("CS420") && password.equals("123");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(2022);
        server.execute();
    }
}

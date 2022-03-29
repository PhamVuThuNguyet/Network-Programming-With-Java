package Review.Login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

        String userName = dataInputStream.readUTF();
        String password = dataInputStream.readUTF();

        int count = dataInputStream.readInt();

        do{
            //Check data
            if (checkData(userName, password)) {
                dataOutputStream.writeUTF("Login Successfully");
            } else {
                if (count < 3) {
                    dataOutputStream.writeUTF("Username or Password incorrect");
                } else {
                    dataOutputStream.writeUTF("Incorrect 3 times");
                    socket.close();
                }
            }
        } while(socket.isConnected());

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

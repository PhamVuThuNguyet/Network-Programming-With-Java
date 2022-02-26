package TCPSocket;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Ex4Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(7000);
        System.out.println("Server is started");

        while (true) {
            Socket socket = server.accept();
            Multi_Threading new_client = new Multi_Threading(socket);
            new Thread(new_client).start();
        }
    }

    private static class Multi_Threading implements Runnable {
        private final Socket clientSocket;

        public Multi_Threading(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                while (true) {
                    String st = dis.readUTF();
                    System.out.println(st);
                    System.out.print("Server: ");
                    String msg = st.split("Client: ")[1].toUpperCase(Locale.ROOT);
                    System.out.println(msg);
                    dos.writeUTF("Server: " + msg);
                    dos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

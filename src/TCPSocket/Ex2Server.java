package TCPSocket;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ex2Server {
    public static void main(String[] args) {
        String data = "Xin chao cac ban cua may tram TCP Socket!";
        try {
            ServerSocket srvr = new ServerSocket(1234);
            Socket skt = srvr.accept();
            System.out.print("May chu da duoc ket noi!\n");
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            System.out.print("Du lieu se gui di cho may tram: '" + data + "'\n");
            out.print(data);
            out.close();
            skt.close();
            srvr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

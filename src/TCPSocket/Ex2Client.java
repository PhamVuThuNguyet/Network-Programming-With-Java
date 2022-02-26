package TCPSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ex2Client {
    public static void main(String[] args) {
        try {
            Socket skt = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(skt.getInputStream()));
            System.out.print("Du lieu da nhan: '");
            System.out.println(in.readLine());
            System.out.print("'\n");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

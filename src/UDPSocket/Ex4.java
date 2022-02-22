/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPSocket;

import java.net.*;
import java.io.*;

public class Ex4 {

    public final static int DEFAULT_PORT = 2002;
    public final static int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {

        int port = DEFAULT_PORT;
        byte[] buffer = new byte[MAX_PACKET_SIZE];

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            DatagramSocket server = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                try {
                    server.receive(packet);
                    String s = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(packet.getAddress() + " at port "
                            + packet.getPort() + " says " + s);
                    // reset the length for the next packet
                    packet.setLength(buffer.length);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

    }
}

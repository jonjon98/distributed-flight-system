import Marshalling.Marshaller;
import Server.serverEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        serverEntity s = new serverEntity();
        s.run();



//        String host = "192.168.188.80"; // replace with the IP address of the client
//        int port = 12345; // replace with the port number the client is listening on
//
//        try {
//            DatagramSocket socket = new DatagramSocket();
//            String response = "Hello";
//            //marshalling
//            Marshaller marshaller = new Marshaller();
//            byte[] responseByteArr = marshaller.marshall(response);
//            System.out.println("responseByteArr: "+responseByteArr.toString());
//            DatagramPacket sendPacket = new DatagramPacket(responseByteArr, responseByteArr.length, InetAddress.getByName(host), port);
//            socket.send(sendPacket);
//            socket.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();}
    }
}

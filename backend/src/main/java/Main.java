import Marshalling.Marshaller;
import Server.serverEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        serverEntity s = new serverEntity();
        s.run();

//        String host = "192.168.188.117"; // replace with the IP address of the client
//        int port = 23456; // replace with the port number the client is listening on
//
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress(host, port));
//            OutputStream output = socket.getOutputStream();
//            System.out.println("Preparing to send");
//            String response = "hello";
//            //marshalling
//            Marshaller marshaller = new Marshaller();
//            byte[] responseByteArr = marshaller.marshall(response);
//
//            // Send response back to client
//            output.write(responseByteArr);
//            System.out.println("Callback response sent");
//        }
//        catch(Exception e){
//            e.printStackTrace();}
    }
}

package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class serverEntity {
    private static final int PORT = 12345;
    public static final int MAX_PACKET_SIZE = 1024;

    public static void run() throws IOException{
        // Create server socket
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            try {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress()
                        + " at port " + clientSocket.getLocalPort());

                // Handle client request
                byte[] request = receive(clientSocket);
                System.out.println("Request received");
                byte[] response = handleRequest(request);
//                System.out.println("Response generated as " + response.toString());

                // Send response back to client
                send(clientSocket, response);
                System.out.println("Response sent");
                // Close client socket
                clientSocket.close();
                System.out.println("Client Socket close");
            } catch (Exception e) {
                System.err.println("Error handling client request: " + e);
            }
        }
    }

    private static void send(Socket clientSocket, byte[] response) throws IOException {
        String responseStr = new String(response);
        if(responseStr.equals("Hello Java!")){
            response = "Hello Python!".getBytes();

        }
        else{
            response = "Hello Java again?".getBytes();
        }
        System.out.println("Response created: " + new String(response));
        OutputStream out = clientSocket.getOutputStream();
        out.write(response);
    }

    private static byte[] receive(Socket clientSocket) throws IOException {
        System.out.println("In receive function");
        InputStream in = clientSocket.getInputStream();
        System.out.println("After inputStream");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.out.println("After ByteArrayOutputStream");

        byte[] buffer = new byte[MAX_PACKET_SIZE];
        int bytesRead;

//        System.out.println("bytesRead is "+ bytesRead);
        while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
            System.out.println("After reading, bytesRead is "+ bytesRead);
            if (bytesRead < buffer.length){
                break;
            }

        }
        String message = new String(out.toByteArray());
        System.out.println("Received message: " + message);



        return out.toByteArray();
    }

    private static byte[] handleRequest(byte[] request) {
        
        //TODO
        return request;
    }


}

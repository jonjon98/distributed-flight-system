package Server;

import Entity.UserInfo;
import Marshalling.Marshaller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class serverEntity {
    private static final int PORT = 12345;
    public static final int MAX_PACKET_SIZE = 1024;

    public void run() throws IOException{
        // create serverDatabase
        serverDatabase serverDatabase = new serverDatabase();

        // Create server socket
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            try {
                // Wait for a client to connect
                System.out.println("waiting to connect...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress()
                        + " at port " + clientSocket.getLocalPort());


                // check if client is existing user
                boolean existingUser = false;
                UserInfo currUser = null;
                for(UserInfo userInfo: serverDatabase.userInfoArrayList){
                    if (userInfo.getIpAdd().equals(clientSocket.getInetAddress().toString())){
                        existingUser = true;
                        currUser = userInfo;
                        break;
                    }
                }
                if (!existingUser){
                    currUser = new UserInfo(
                            clientSocket.getInetAddress().toString(),
                            "at-most-once");
                    serverDatabase.userInfoArrayList.add(currUser);
                }

                // Handle client request
                byte[] request = receive(clientSocket);
                System.out.println("Request received");
                Marshaller marshaller = new Marshaller();
                HashMap<String, String> requestQuery = marshaller.unmarshall(request);
                System.out.println("Hashmap: "+ requestQuery.toString());
                System.out.println("Semantics: " + currUser.getSemantics());
                String response;
                // check semantics
                // execute request if semantics is at-least-once
                if (serverController.checkSemantics(requestQuery, currUser)){
                    // if not duplicate request, handle request
                    // save the request and its response
                    response = handleRequest(requestQuery, currUser);
                    currUser.setResponse(requestQuery.toString(), response);
//                  System.out.println("Response generated as " + response.toString());
                }
                else{
                    // if at-most-once request
                    response = currUser.getResponse(requestQuery.toString());
                    // generate reponse if not generated before
                    if (response == null){
                        System.out.println("Duplicate request, sending stored info...");
                        response = handleRequest(requestQuery, currUser);
                    }
                    currUser.setResponse(requestQuery.toString(), response);
                }

                //unmarshalling
                byte[] responseByteArr = marshaller.marshall(response);

                // Send response back to client
                send(clientSocket, responseByteArr);
                System.out.println("Response sent");

                // Close client socket
                clientSocket.close();
                System.out.println("Client Socket close");
            } catch (Exception e) {
                System.err.println("Error handling client request: " + e);
            }
        }
    }

    private void send(Socket clientSocket, byte[] response) throws IOException {
        String responseStr = new String(response);
//        if(responseStr.equals("Hello Java!")){
//            response = "Hello Python!".getBytes();
//
//        }
//        else{
//            response = "Hello Java again?".getBytes();
//        }
        System.out.println("Response created: " + new String(response));
        OutputStream out = clientSocket.getOutputStream();
        out.write(response);
    }

    private byte[] receive(Socket clientSocket) throws IOException {
        System.out.println("In receive function");
        InputStream in = clientSocket.getInputStream();
        System.out.println("After inputStream "+ in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.out.println("After ByteArrayOutputStream");

        byte[] buffer = new byte[MAX_PACKET_SIZE];
        int bytesRead;

//        System.out.println("bytesRead is "+ bytesRead);
        while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
//            System.out.println("After reading, bytesRead is "+ bytesRead);
//            System.out.println("After reading, out is "+ out);
            if (bytesRead < buffer.length){
                break;
            }

        }
        System.out.println("Received out: " + out);
//        System.out.println("Received buffer: " + buffer[0]);
//        for(int i = 1; i<3; i++){
//            System.out.print((char) buffer[i]);
//        }
        String message = new String(out.toByteArray());
        System.out.println("Received message: " + message);



        return out.toByteArray();
    }

    public String handleRequest(HashMap<String, String> request, UserInfo userInfo) {
        String requestQueryId = request.get("QueryId");
        String response;
        switch (requestQueryId){
            case "1":
                System.out.println("Enter Query 1");
                // query flight location
                response = serverController.getFlightID(
                        request.get("source"),
                        request.get("destination")
                );
                break;

            case "2":
                // query flight info
                response = serverController.getFlightInfo(request.get("flightId"));
                break;

            case "3":
                // create flight booking
                response = serverController.flightBooking(
                        request.get("flightId"),
                        Integer.parseInt(request.get("numOfSeats"))
                );
                break;

            case "4":
                // create callback
                response = serverController.callbackRequest(
                        userInfo.getIpAdd(), request.get("flightId"),
                        Integer.parseInt(request.get("requestMinutes"))
                );
                break;

            case "5":
                // retrieve user booking
                response = serverController.retrieveBooking(request.get("bookingId"));
                break;

            case "6":
                // cancel user booking
                response = serverController.cancelBooking(request.get("bookingId"));
                break;

            case "7":
                // display service page
                response = serverController.displayServicesPage();
                break;

            case "8":
                // display semantics page
                response = serverController.displaySemanticsPage();
                break;

            default:
                response = "New Query Id? Dont funny leh";
                System.out.println(response);
        }
        //TODO
//        byte[] byteRequest = response.getBytes();
        return response;
    }


}

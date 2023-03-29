package Server;

import Entity.FlightInfo;
import Entity.UserInfo;
import Marshalling.Marshaller;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class serverEntity {
    private static final int PORT = 12345;
    public static final int MAX_PACKET_SIZE = 2048;
    public static String callbackChecker = null;

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
                            clientSocket.getLocalPort(),
                            "at-least-once");
                    serverDatabase.userInfoArrayList.add(currUser);
                }

                // Handle client request
                byte[] request = receive(clientSocket);
                System.out.println("Request received");
                Marshaller marshaller = new Marshaller();
                HashMap<String, String> requestQuery = marshaller.unmarshall(request);
//                System.out.println("Hashmap: "+ requestQuery.toString());
//                System.out.println("Semantics: " + currUser.getSemantics());
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
                        // System.out.println("Duplicate request, sending stored info...");
                        response = handleRequest(requestQuery, currUser);
                    }
                    currUser.setResponse(requestQuery.toString(), response);
                }
//                System.out.println("Before marshalling: "+response);
                //marshalling
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
//            System.out.println("Main serverEntity.callbackChecker: "+ callbackChecker);
            // check if callbackChecker is null
            System.out.print("callbackChecker!=null: "+callbackChecker!=null);
            if(callbackChecker!=null){
                FlightInfo callbackFlight = null;
                for(FlightInfo flightInfo: serverDatabase.flightInfoArrayList){
//                    System.out.println("Looping through flighID: "+flightInfo.getFlightId());
//                    System.out.println("Correct? "+callbackChecker.equals(flightInfo.getFlightId()));
                    if(callbackChecker.equals(flightInfo.getFlightId())){
                        System.out.println("Enter sending callback");
                        callbackFlight = flightInfo;
                        for(UserInfo callbackUser: serverDatabase.callbackHmap.get(callbackChecker)){

                            String host = callbackUser.getIpAdd().replace("/", ""); // replace with the IP address of the client
                            int port = 23456; // replace with the port number the client is listening on

//                            System.out.println("callbackUser.getIpAdd(): "+callbackUser.getIpAdd());
//                            System.out.println("host: "+host);
//                            System.out.println("port: "+port);

                            try (Socket socket = new Socket()) {
                                socket.connect(new InetSocketAddress(InetAddress.getByName(host), port),
                                        5000);
                                OutputStream output = socket.getOutputStream();
//                                System.out.println("Preparing to send");
                                String response = callbackFlight.toString();
                                //marshalling
                                Marshaller marshaller = new Marshaller();
                                byte[] responseByteArr = marshaller.marshall(response);

                                // Send response back to client
                                output.write(responseByteArr);
                                System.out.println("Callback response sent");
                            }
                            catch(Exception e){
                                e.printStackTrace();}
//                            break;
                        }
                    }
                }
                callbackChecker = null;
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
//        System.out.println("In receive function");
        InputStream in = clientSocket.getInputStream();
//        System.out.println("After inputStream "+ in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.out.println("After ByteArrayOutputStream");

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
        String requestId = request.get("id");
        String requestCommand = request.get("command");
        String response;

        System.out.println("The requestId is: " + requestId);
        switch (requestCommand){
            case "queryID":
                System.out.println("Enter request queryID");
                // query flight location
                response = serverController.getFlightID(
                        request.get("source").toUpperCase(),
                        request.get("destination").toUpperCase()
                );
                break;

            case "queryDetails":
                System.out.println("Enter request queryDetails");
                // query flight info
                response = serverController.getFlightInfo(request.get("flightID"));
                break;

            case "reserve":
                System.out.println("Enter request reserve");
                // create flight booking
                response = serverController.flightBooking(
                        request.get("flightID"),
                        Integer.parseInt(request.get("noOfSeats"))
                );

                break;

            case "subscribe":
                System.out.println("Enter request subscribe");
                // create callback
                response = serverController.createCallback(
                        request.get("flightID"),
                        Integer.parseInt(request.get("interval")),
                        userInfo
                );
                break;

            case "cancelCallback":
                System.out.println("Enter cancelCallback");
                // stop callback
                response = serverController.cancelCallback(
                        request.get("flightID"),
                        userInfo
                );
                break;

            case "retrieve":
                System.out.println("Enter request retrieve");
                // retrieve user booking
                response = serverController.retrieveBooking(request.get("bookingID"));
                break;

            case "cancel":
                System.out.println("Enter request cancel");
                // cancel user booking
                response = serverController.cancelBooking(request.get("bookingID"));
                break;
            
            // case "setSemantics":
            //     System.out.println("Enter request setSemantics");
            //     // create setSemantics
            //     response = serverController.setSemantics(
            //             request.get("setSemantics")
            //     );
            //     break;

//            case "displayServices":
//                // display service page
//                response = serverController.displayServicesPage();
//                break;

            case "config":
                // display semantics page
                // response = serverController.displaySemanticsPage();
                userInfo.setSemantics(request.get("semantics"));
                response = userInfo.getSemantics();
                break;

            default:
                response = "New Query Id? Dont funny leh";
                System.out.println(response);
        }

        return response;
    }


}

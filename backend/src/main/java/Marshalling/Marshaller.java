package Marshalling;

import Server.serverController;
import com.google.common.primitives.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import static Server.serverEntity.MAX_PACKET_SIZE;

public class Marshaller {

    public static int MAX_INT_LENGTH = 3;
    public byte[] marshall(String response){
        StringBuilder responseBuilder = new StringBuilder();

//        String key = "response";
//        int keyLen = key.length();
//        responseBuilder.append(String.format("%03d", keyLen));
//        responseBuilder.append(key);

//        int reponseLen = response.length();
//        responseBuilder.append(String.format("%03d", reponseLen));
        responseBuilder.append(response);

        byte[] responseByteArr = responseBuilder.toString().getBytes(StandardCharsets.UTF_16BE);
        // return marshalled information
        return responseByteArr;
    }

    public HashMap<String, String> unmarshall(byte[] inByteArr) throws IOException {
        // return information (may or may not be string) from bytes[]
        int bytesPos = 0;
        List<Byte> byteList = new LinkedList<>(Bytes.asList(inByteArr));

        HashMap<String, String> unmarshallMap = new HashMap<String, String>();

        StringBuilder lenBuilder = new StringBuilder();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valBuilder = new StringBuilder();

        String commandValue = null;

        // first filter out id and command
        while(true) {
            // extract len of key + string of key
//            System.out.println( inByteArr[bytesPos]);
            //allocate first 3 bytes for length
            for (int i = 0; i < MAX_INT_LENGTH; i++) {
//                lenBuilder.append((char) inByteArr[bytesPos]);
                lenBuilder.append((char) byteList.remove(0).byteValue());
                bytesPos += 1;
            }
            int keyLength = Integer.parseInt(lenBuilder.toString());
            System.out.println("keyLength: " + keyLength);

            // find string of key
            while (keyLength > 0) {
                keyBuilder.append((char) byteList.remove(0).byteValue());
                keyLength -= 1;
                bytesPos += 1;
            }
            String keyString = keyBuilder.toString();
            System.out.println("keyString: " + keyString);
            lenBuilder.setLength(0);
            keyBuilder.setLength(0);

            //allocate first 3 bytes for length
            for (int i = 0; i < MAX_INT_LENGTH; i++) {
                lenBuilder.append((char) byteList.remove(0).byteValue());
                bytesPos += 1;
            }
            int valLength = Integer.parseInt(lenBuilder.toString());
            System.out.println("keyLength: " + valLength);

            while (valLength > 0) {
                valBuilder.append((char) byteList.remove(0).byteValue());
                valLength -= 1;
                bytesPos += 1;
            }
            String valString = valBuilder.toString();
            System.out.println("keyString: " + valString);

            unmarshallMap.put(keyString, valString);
            valBuilder.setLength(0);
            lenBuilder.setLength(0);
            if (keyString.equals("command")) {
                commandValue = valString;
                break;
            }
        }

        // process each command differently
        Integer keyValCount = 0;
        switch(commandValue){

            case "queryID", "reserve", "subscribe":
                keyValCount = 2;
                break;

            case "queryDetails", "cancelCallback", "retrieve", "cancel":
                keyValCount = 1;
                break;

            default:
                keyValCount = 0;
                break;
        }
        while(keyValCount>0){
            //allocate first 3 bytes for length
            for(int i = 0; i<MAX_INT_LENGTH; i++){
//                lenBuilder.append((char) inByteArr[bytesPos]);
                lenBuilder.append((char) byteList.remove(0).byteValue());
                bytesPos += 1;
            }
            int keyLength = Integer.parseInt(lenBuilder.toString());

            // find string of key
            while(keyLength > 0){
                keyBuilder.append((char) byteList.remove(0).byteValue());
                keyLength -= 1;
                bytesPos += 1;
            }
            String keyString = keyBuilder.toString();
            System.out.println("keyString: "+keyString);
            lenBuilder.setLength(0);
            keyBuilder.setLength(0);

            //allocate first 3 bytes for length
            for(int i = 0; i<MAX_INT_LENGTH; i++){
                lenBuilder.append((char) byteList.remove(0).byteValue());
                bytesPos += 1;
            }
            Integer valLength = Integer.parseInt(lenBuilder.toString());
            while(valLength > 0){
                valBuilder.append((char) byteList.remove(0).byteValue());
                valLength -= 1;
                bytesPos += 1;
            }
            String valString = valBuilder.toString();
            System.out.println("valString: "+valString);

            unmarshallMap.put(keyString, valString);
            valBuilder.setLength(0);
            lenBuilder.setLength(0);

            keyValCount--;
        }
        return unmarshallMap;
    }
}

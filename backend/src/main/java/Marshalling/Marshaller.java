package Marshalling;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


import static Server.serverEntity.MAX_PACKET_SIZE;

public class Marshaller {

    public static int MAX_INT_LENGTH = 3;
    public byte[] marshall(String response){
        // iterate through the hashmap
        // for each key value, find len and at as string
        StringBuilder responseBuilder = new StringBuilder();
//        for(Map.Entry<String, String> param: response.entrySet()){
//            // find len
//            int keyLen = param.getKey().length();
//            responseBuilder.append(String.format("%03d", keyLen));
//            responseBuilder.append(param.getKey());
//
//            int valLen = param.getKey().length();
//            responseBuilder.append(String.format("%03d", valLen));
//            responseBuilder.append(param.getValue());
//
//        }
        String key = "response";
        int keyLen = key.length();
        responseBuilder.append(String.format("%03d", keyLen));
        responseBuilder.append(key);

        int reponseLen = response.length();
        responseBuilder.append(String.format("%03d", reponseLen));
        responseBuilder.append(response);

        byte[] responseByteArr = responseBuilder.toString().getBytes(StandardCharsets.UTF_16BE);
        // return marshalled information
        return responseByteArr;
    }

    public HashMap<String, String> unmarshall(byte[] inByteArr) throws IOException {
        // return information (may or may not be string) from bytes[]
        int bytesPos = 0;

        HashMap<String, String> unmarshallMap = new HashMap<String, String>();

        StringBuilder lenBuilder = new StringBuilder();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valBuilder = new StringBuilder();

        while(bytesPos < inByteArr.length){
            // extract len of key + string of key
            System.out.println( inByteArr[bytesPos]);
            //allocate first 3 bytes for length
            for(int i = 0; i<MAX_INT_LENGTH; i++){
                lenBuilder.append((char) inByteArr[bytesPos]);
                bytesPos += 1;
            }
            int keyLength = Integer.parseInt(lenBuilder.toString());

            // find string of key
            while(keyLength > 0){
                keyBuilder.append((char) inByteArr[bytesPos]);
                keyLength -= 1;
                bytesPos += 1;
            }
            String keyString = keyBuilder.toString();
            lenBuilder.setLength(0);
            keyBuilder.setLength(0);

            //allocate first 3 bytes for length
            for(int i = 0; i<MAX_INT_LENGTH; i++){
                lenBuilder.append((char) inByteArr[bytesPos]);
                bytesPos += 1;
            }
            int valLength = Integer.parseInt(lenBuilder.toString());
            while(valLength > 0){
                valBuilder.append((char) inByteArr[bytesPos]);
                valLength -= 1;
                bytesPos += 1;
            }
            String valString = valBuilder.toString();

            unmarshallMap.put(keyString, valString);
            valBuilder.setLength(0);
            lenBuilder.setLength(0);
        }
        return unmarshallMap;
    }
}

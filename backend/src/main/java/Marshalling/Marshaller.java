package Marshalling;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

import static Server.serverEntity.MAX_PACKET_SIZE;

public class Marshaller {

    public byte[] marshall(){
        // return marshalled information
        return "Marshall Test".getBytes();
    }

    public String unmarshall(InputStream in) throws IOException {
        // return information (may or may not be string) from bytes[]
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[MAX_PACKET_SIZE];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
            System.out.println("After reading, bytesRead is "+ bytesRead);
            if (bytesRead < buffer.length){
                break;
            }

        }
        String message = new String(out.toByteArray());
        System.out.println("Received message: " + message);
        return message;
    }
}

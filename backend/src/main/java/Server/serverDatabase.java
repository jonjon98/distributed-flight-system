package Server;

import Entity.FlightInfo;

import java.util.ArrayList;

public class serverDatabase {

    private static ArrayList<FlightInfo> flightData= new ArrayList<FlightInfo>();;
//    private static HashMap<Integer, List<ClientInfo>> callbackList = new HashMap<>();

    public serverDatabase(){
        seed();
    }

    public static void seed(){
        // DateTime dateTime = new DateTime(2019,3,28,23,5);
        // flightData.add(new FlightInfo(,"SINGAPORE","BANGKOK",dateTime,10.23F,(short) 5));
        // dateTime = new DateTime(2019,3,28,23,10);
        // flightData.add(new FlightInfo((short) 2012,"KL","BANGKOK",dateTime,2.23F,(short) 15));
        // dateTime = new DateTime(2019,3,28,23,15);
        // flightData.add(new FlightInfo((short) 3197,"SINGAPORE","KL",dateTime,3.23F,(short) 2));
        // dateTime = new DateTime(2019,3,28,23,20);
        // flightData.add(new FlightInfo((short) 4456,"BANGKOK","SINGAPORE",dateTime,4.23F,(short) 9));
        // dateTime = new DateTime(2019,3,28,23,25);
        // flightData.add(new FlightInfo((short) 5232,"SINGAPORE","BANGKOK",dateTime,5.23F,(short) 7));
    }
}

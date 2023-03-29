package Server;

import Entity.BookingInfo;
import Entity.FlightInfo;
import Entity.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class serverDatabase {

    public static ArrayList<FlightInfo> flightInfoArrayList= new ArrayList<FlightInfo>();
    public static ArrayList<UserInfo> userInfoArrayList = new ArrayList<UserInfo>();
    public static ArrayList<BookingInfo> bookingInfoArrayList = new ArrayList<BookingInfo>();
    public static HashMap<String, ArrayList<UserInfo>> callbackHmap = new HashMap<>();

    public serverDatabase(){
        generate();
    }

    public static void generate(){
        int year = 2023;
        String source = "SINGAPORE";

        // create the FlightInfo objects
        FlightInfo flight1 = new FlightInfo(source, "BANGKOK", LocalDateTime.of(year, 4, 3, 12, 30), 220.50f, 30);
        FlightInfo flight2 = new FlightInfo(source, "CHINA", LocalDateTime.of(year, 4, 4, 10, 0), 300.00f, 25);
        FlightInfo flight3 = new FlightInfo(source, "TAIWAN", LocalDateTime.of(year, 4, 5, 14, 45), 250.75f, 40);
        FlightInfo flight4 = new FlightInfo(source, "JAPAN", LocalDateTime.of(year, 4, 7, 6, 15), 280.25f, 35);
        FlightInfo flight5 = new FlightInfo(source, "MALAYSIA", LocalDateTime.of(year, 4, 9, 16, 0), 225.00f, 45);
        FlightInfo flight6 = new FlightInfo(source, "BANGKOK", LocalDateTime.of(year, 4, 11, 9, 30), 240.75f, 30);
        FlightInfo flight7 = new FlightInfo(source, "CHINA", LocalDateTime.of(year, 4, 13, 13, 15), 280.25f, 25);
        FlightInfo flight8 = new FlightInfo(source, "TAIWAN", LocalDateTime.of(year, 4, 15, 17, 45), 255.5f, 40);
        FlightInfo flight9 = new FlightInfo(source, "JAPAN", LocalDateTime.of(year, 4, 17, 8, 0), 290.00f, 35);
        FlightInfo flight10 = new FlightInfo(source, "MALAYSIA", LocalDateTime.of(year, 4, 19, 11, 30), 230.25f, 45);

        // add the FlightInfo objects to the ArrayList
        flightInfoArrayList.add(flight1);
        flightInfoArrayList.add(flight2);
        flightInfoArrayList.add(flight3);
        flightInfoArrayList.add(flight4);
        flightInfoArrayList.add(flight5);
        flightInfoArrayList.add(flight6);
        flightInfoArrayList.add(flight7);
        flightInfoArrayList.add(flight8);
        flightInfoArrayList.add(flight9);
        flightInfoArrayList.add(flight10);

        // pre add callbackHmap
        for(FlightInfo flightInfo: flightInfoArrayList){
            callbackHmap.put(flightInfo.getFlightId(), new ArrayList<UserInfo>());
        }


    }
}

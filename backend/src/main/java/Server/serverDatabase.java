package Server;

import Entity.BookingInfo;
import Entity.FlightInfo;
import Entity.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class serverDatabase {

    public static ArrayList<FlightInfo> flightInfoArrayList= new ArrayList<FlightInfo>();
    public static ArrayList<UserInfo> userInfoArrayList = new ArrayList<UserInfo>();
    public static ArrayList<BookingInfo> bookingInfoArrayList = new ArrayList<BookingInfo>();
//    private static HashMap<Integer, List<ClientInfo>> callbackList = new HashMap<>();

    public serverDatabase(){
        generate();
    }

    public static void generate(){
        int year = 2023;
        int month = 4;
        String source = "SINGAPORE";
//        List<String> destination = List.of("BANGKOK", "CHINA", "TAIWAN", "JAPAN", "MALAYSIA");
        List<String> destination = List.of("CHINA");
        Random rand = new Random();
        int cost = 200;
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);

        for (int i = 0; i < 50; i++) {
            LocalDateTime dateTime = start.plusDays(rand.nextInt(0,5)).plusHours(rand.nextInt(0,23)).plusMinutes(rand.nextInt(0,59));
            String dest = destination.get(rand.nextInt(destination.size()));
            float airfare = Math.round(rand.nextFloat() * cost + 200 * 100.0f) / 100.0f;
            flightInfoArrayList.add(new FlightInfo(source,
                    dest,dateTime,airfare,50));
        }

    }
}

package Server;

import Entity.BookingInfo;
import Entity.FlightInfo;
import Entity.UserInfo;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class serverController {

    public static String displayServicesPage(){
//        StringBuilder displayBuilder = new StringBuilder();
//        displayBuilder.append("""
//                Services Provided:\n
//                1. Search for flight ID\n
//                2. Check flight details\n
//                3. Book a flight\n
//                4. Set update for flight ID\n
//                5. Retrieve flight booking information\n
//                6. Cancel existing booking reservation\n
//                7. Previous Page\n\n
//                Service selected (Number only):
//                """);
//        System.out.println(displayBuilder);
//        return displayBuilder.toString();
        String displayService = """
                Services Provided:\n
                1. Search for flight ID\n
                2. Check flight details\n
                3. Book a flight\n
                4. Set update for flight ID\n
                5. Retrieve flight booking information\n
                6. Cancel existing booking reservation\n
                7. Previous Page\n\n
                Service selected (Number only):  
                """;
        System.out.println(displayService);
        return displayService;
    }

    public static String displaySemanticsPage(){
        String displaySemantics = """
                Semantics Option:\n
                1. at-least-once\n
                2. at-most-once\n
                3. Exit\n\n
                Option selected (Number only):  
                """;
        System.out.println(displaySemantics);
        return displaySemantics;
    }

    public static String getFlightID(String sourcePlace, String destinationPlace){
        StringBuilder flightInformation = new StringBuilder();
//        System.out.println("serverDatabase.flightInfoArrayList: "+serverDatabase.flightInfoArrayList.toString());
        for(FlightInfo flight: serverDatabase.flightInfoArrayList){
            System.out.println("sourcePlace: "+sourcePlace);
            System.out.println("flight.getSource(): "+flight.getSource());
            System.out.println("destinationPlace: "+destinationPlace);
            System.out.println("flight.getDestination(): "+flight.getDestination());
            System.out.println("bool sourcePlace: "+sourcePlace.equals(flight.getSource()));
            System.out.println("bool destinationPlace: "+destinationPlace.equals(flight.getDestination()));
            if(sourcePlace.equals(flight.getSource()) && destinationPlace.equals(flight.getDestination())){
                flightInformation.append("FlightID: "+ flight.getFlightId() +"\n");
                flightInformation.append("Source: "+ flight.getSource() +"\n");
                flightInformation.append("Destination: "+ flight.getDestination() +"\n\n");
            }
        }
        System.out.println(flightInformation);
        return flightInformation.toString();
    }

    public static String getFlightInfo(String flightId){
        StringBuilder flightInformation = new StringBuilder();
        for(FlightInfo flight: serverDatabase.flightInfoArrayList){
            if(flightId.equals(flight.getFlightId())){
                flightInformation.append("Departure Time: " +
                        flight.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - h:mmA")) + "\n");
                flightInformation.append("Airfare: "+ flight.getAirfare() +"\n");
                flightInformation.append("Seats Availability: "+ flight.getSeatAvail() +"\n\n");
                break;
            }
        }
        return flightInformation.toString();
    }

    public static String flightBooking(String flightId, int seats){
        StringBuilder flightInformation = new StringBuilder();
        boolean found = false;
        for(FlightInfo flight: serverDatabase.flightInfoArrayList){
            if(flightId.equals(flight.getFlightId())) {
                found = true;
                if (flight.getSeatAvail() >= seats) {
                    BookingInfo booking = new BookingInfo(
                            flightId,
                            flight.getSource(),
                            flight.getDestination(),
                            flight.getDepartureTime(),
                            flight.getAirfare() * seats,
                            seats);
                    serverDatabase.bookingInfoArrayList.add(booking);
                    flightInformation.append("Booking has been confirmed for the following:\n");
                    flightInformation.append("Booking ID: " + booking.getBookingId()+ "\n");
                    flightInformation.append("Flight ID: " + booking.getFlightId()+ "\n");
                    flightInformation.append("Source: " + booking.getSource()+ "\n");
                    flightInformation.append("Destination: " + booking.getDestination()+ "\n");
                    flightInformation.append("Departure Time: " +
                            booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - h:mmA")) + "\n");
                    flightInformation.append("Total Airfare: " + booking.getAirfare()+ "\n");
                    flightInformation.append("Number of seats booked: " + booking.getSeatsBooked()+ "\n\n");
                    break;
                }
                else if(flight.getSeatAvail()==0) {
                    flightInformation.append("There are no more seats for the current flight!\n\n");
                }
                else {
                    flightInformation.append("The seats request cannot be more than the number of seats left!\n\n");
                }
            }
        }
        if(!found){
            flightInformation.append("The given flight ID does not exist!\n\n");
        }
        return flightInformation.toString();
    }

    public static String callbackRequest(String ipAdd, String flightId, int monitorMinutes){
        UserInfo callbackUser;
        boolean found = false;
        StringBuilder callbackAck = new StringBuilder();
        for(UserInfo user: serverDatabase.userInfoArrayList){
            if(user.getIpAdd().equals(ipAdd)){
                callbackUser = user;

                for(FlightInfo flight: serverDatabase.flightInfoArrayList){
                    if(flight.getFlightId().equals(flightId)){
                        found = true;
                        callbackUser.setCallbackFlight(flightId);
                        callbackAck.append("Flight ID "+callbackUser.getCallbackFlight()+" is current being monitored for "+
                                monitorMinutes + " minutes...\n\n");
                    }
                }
            }
        }
        if(!found){
            callbackAck.append("The given flight ID does not exist!\n\n");
        }
        return callbackAck.toString();
    }

    public static String retrieveBooking(String bookingId){
        StringBuilder bookingInformation = new StringBuilder();
        boolean found = false;
        for(BookingInfo booking: serverDatabase.bookingInfoArrayList){
            if(bookingId.equals(booking.getBookingId())) {
                found = true;
                bookingInformation.append("Booking has been confirmed for the following:\n");
                bookingInformation.append("Booking ID: " + booking.getBookingId()+ "\n");
                bookingInformation.append("Flight ID: " + booking.getFlightId()+ "\n");
                bookingInformation.append("Source: " + booking.getSource()+ "\n");
                bookingInformation.append("Destination: " + booking.getDestination()+ "\n");
                bookingInformation.append("Departure Time: " +
                        booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - h:mmA")) + "\n");
                bookingInformation.append("Total Airfare: " + booking.getAirfare()+ "\n");
                bookingInformation.append("Number of seats booked: " + booking.getSeatsBooked()+ "\n\n");
                break;
            }
        }
        if(!found){
            bookingInformation.append("The given booking ID does not exist!\n\n");
        }
        return bookingInformation.toString();
    }

    public static String cancelBooking(String bookingId){
        StringBuilder bookingInformation = new StringBuilder();
        boolean found = false;
        for(BookingInfo booking: serverDatabase.bookingInfoArrayList){
            if(bookingId.equals(booking.getBookingId())) {
                found = true;
                bookingInformation.append("Booking has been cancelled for the following:\n");
                bookingInformation.append("Booking ID: " + booking.getBookingId()+ "\n");
                bookingInformation.append("Flight ID: " + booking.getFlightId()+ "\n");
                bookingInformation.append("Source: " + booking.getSource()+ "\n");
                bookingInformation.append("Destination: " + booking.getDestination()+ "\n");
                bookingInformation.append("Departure Time: " +
                        booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - h:mmA")) + "\n");
                bookingInformation.append("Total Airfare: " + booking.getAirfare()+ "\n");
                bookingInformation.append("Number of seats booked: " + booking.getSeatsBooked()+ "\n\n");
                break;
            }
        }
        if(!found){
            bookingInformation.append("The given booking ID does not exist!\n\n");
        }
        return bookingInformation.toString();
    }

    public static boolean checkSemantics(HashMap<String, String> requestQuery, UserInfo userInfo){
        // checkSemantics logic 
        String semantics = userInfo.getSemantics();
        if (semantics == "at-least-once") return true;
        else return false;
    }




}

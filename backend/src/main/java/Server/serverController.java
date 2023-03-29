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

    public static String getFlightID(String sourcePlace, String destinationPlace){
        StringBuilder flightInformation = new StringBuilder();
//        System.out.println("serverDatabase.flightInfoArrayList: "+serverDatabase.flightInfoArrayList.toString());
        for(FlightInfo flight: serverDatabase.flightInfoArrayList){
//            System.out.println("sourcePlace: "+sourcePlace);
//            System.out.println("flight.getSource(): "+flight.getSource());
//            System.out.println("destinationPlace: "+destinationPlace);
//            System.out.println("flight.getDestination(): "+flight.getDestination());
//            System.out.println("bool sourcePlace: "+sourcePlace.equals(flight.getSource()));
//            System.out.println("bool destinationPlace: "+destinationPlace.equals(flight.getDestination()));
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
                        flight.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - HH:mm a")) + "\n");
                flightInformation.append("Airfare: $"+ String.format("%.2f", flight.getAirfare()) +"\n");
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
                            booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - HH:mm a")) + "\n");
                    flightInformation.append("Total Airfare: $"+ String.format("%.2f", booking.getAirfare()) +"\n");
                    flightInformation.append("Number of seats booked: " + booking.getSeatsBooked()+ "\n\n");
                    int seatAvailability = flight.getSeatAvail();
                    flight.setSeatAvail(seatAvailability - booking.getSeatsBooked());
                    // set callbackChecker = flightId
                    serverEntity.callbackChecker = flightId;
//                    System.out.println("serverEntity.callbackChecker: "+ serverEntity.callbackChecker);
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

    public static String createCallback(String flightId, int monitorMinutes, UserInfo callbackUser){
        StringBuilder callbackAck = new StringBuilder();
        for(FlightInfo flight: serverDatabase.flightInfoArrayList){
            // if flightID generated is correct, add UserInfo to callbackHmap
            if(flight.getFlightId().equals(flightId)){
                serverDatabase.callbackHmap.get(flightId).add(callbackUser);
                callbackAck.append("Flight ID "+flightId+" is current being monitored for "+
                        monitorMinutes + " minutes...\n\n");
                System.out.println(callbackAck);
            }
        }
        return callbackAck.toString();
    }


    public static String cancelCallback(String flightId, UserInfo callbackUser){
        StringBuilder callbackAck = new StringBuilder();
        serverDatabase.callbackHmap.get(flightId).remove(callbackUser);
        callbackAck.append("Monitoring has stopped\n\n");
        System.out.println(callbackAck);
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
                        booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - HH:mm a")) + "\n");
                bookingInformation.append("Total Airfare: $"+ String.format("%.2f", booking.getAirfare()) +"\n");
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
                        booking.getDepartureTime().format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - HH:mm a")) + "\n");
                bookingInformation.append("Total Airfare: $"+ String.format("%.2f", booking.getAirfare()) +"\n");
                bookingInformation.append("Number of seats booked: " + booking.getSeatsBooked()+ "\n\n");

                for (FlightInfo flight: serverDatabase.flightInfoArrayList){
                    if (booking.getFlightId().equals(flight.getFlightId())){
                        int seatAvailability = flight.getSeatAvail();
                        flight.setSeatAvail(seatAvailability + booking.getSeatsBooked());
                    }
                }
                serverDatabase.bookingInfoArrayList.remove(booking);
                serverEntity.callbackChecker = booking.getFlightId();
//                System.out.println("serverEntity.callbackChecker: "+ serverEntity.callbackChecker);
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

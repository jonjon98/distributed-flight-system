package Entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FlightInfo {
    private String flightId;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private float airfare;
    private Integer seatAvail;

    public FlightInfo(String source, String destination,
                      LocalDateTime departureTime, float airfare, Integer seatAvail){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        this.flightId = departureTime.format(formatter);
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.seatAvail = seatAvail;

    }

    @Override
    public String toString() {
        return "The following flight information has been updated:\n" +
                "Flight ID: " + flightId + "\n" +
                "Source: " + source + "\n" +
                "Destination: " + destination + "\n" +
                "Departure Time: " + departureTime.format(DateTimeFormatter.ofPattern("EEE, dd/MMM/yyyy - HH:mm a")) + "\n" +
                "Airfare: $"+ String.format("%.2f", airfare) + "\n" +
                "Available Seats: " + seatAvail;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public float getAirfare() {
        return airfare;
    }

    public void setAirfare(float airfare) {
        this.airfare = airfare;
    }

    public Integer getSeatAvail() {
        return seatAvail;
    }

    public void setSeatAvail(Integer seatAvail) {
        this.seatAvail = seatAvail;
    }
}

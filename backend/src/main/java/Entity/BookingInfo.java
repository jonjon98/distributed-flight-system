package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Timestamp;

public class BookingInfo {

    private String bookingId;
    private String flightId;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private float airfare;
    private Integer seatsBooked;

    public BookingInfo(){
        this.bookingId = getBookingId();
        this.flightId = getFlightId();
        this.source = getSource();
        this.destination = getDestination();
        this.departureTime = getDepartureTime();
        this.airfare = getAirfare();
        this.seatsBooked = getSeatsBooked();
    }

    public BookingInfo(String flightId, String source, String destination, LocalDateTime departureTime,
                       float airfare, int seatsBooked){
        StringBuilder bookingBuilder = new StringBuilder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.bookingId = String.valueOf(timestamp.getTime());
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.seatsBooked = seatsBooked;
    }


    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }
}

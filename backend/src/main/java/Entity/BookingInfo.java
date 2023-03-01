package Entity;

import java.util.Date;

public class BookingInfo {

    private String bookingId;
    private String flightId;
    private String source;
    private String destination;
    private Date departureTime;
    private float airfare;
    private Integer seatsBooked;

    public BookingInfo(){
        this.flightId = getFlightId();
        this.source = getSource();
        this.destination = getDestination();
        this.departureTime = getDepartureTime();
        this.airfare = getAirfare();
        this.seatsBooked = getSeatsBooked();

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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
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

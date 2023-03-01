package Entity;

import com.google.type.DateTime;

import java.util.Date;

public class FlightInfo {
    private String flightId;
    private String source;
    private String destination;
    private DateTime departureTime;
    private float airfare;
    private Integer seatAvail;

    public FlightInfo(String flightId, String source, String destination,
                      DateTime departureTime, float airfare, Integer seatAvail){
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.seatAvail = seatAvail;

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

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
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

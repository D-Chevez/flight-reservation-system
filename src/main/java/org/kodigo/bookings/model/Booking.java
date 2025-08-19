package org.kodigo.bookings.model;

import org.kodigo.flights.model.Flight;
import org.kodigo.passengers.model.Passenger;
import org.kodigo.shared.money.Money;

import java.util.UUID;

public final class Booking {

    public enum BookingState {
        CONFIRMED, CANCELLED, CHECKED_IN
    }

    private final UUID id;
    private final String code;
    private final Flight flight;
    private final Passenger passenger;
    private final String seatNumber;
    private Money total;
    private BookingState state;

    public Booking(String code, Flight flight, Passenger passenger, String seatNumber, Money total) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.flight = flight;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
        this.total = total;
        this.state = BookingState.CONFIRMED;
    }

    public UUID id() {
        return id;
    }

    public String code() {
        return code;
    }

    public Flight flight() {
        return flight;
    }

    public Passenger passenger() {
        return passenger;
    }

    public String seatNumber() {
        return seatNumber;
    }

    public BookingState state() {
        return state;
    }

    public Money total() {
        return total;
    }

    // State transitions
    public void confirm()
    {
        state = BookingState.CONFIRMED;
    }

    public void cancel()
    {
        state = BookingState.CANCELLED;
    }

    public void checkIn()
    {
        state = BookingState.CHECKED_IN;
    }
}
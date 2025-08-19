package org.kodigo.flights.model;

import java.util.Objects;
import java.util.UUID;

public class Seat {
    private final UUID id;
    private final String number;
    private final SeatClass seatClass;

    public enum SeatClass { ECONOMY, PREMIUM_ECONOMY, BUSINESS }

    public Seat(String number, SeatClass seatClass) {
        this.id = UUID.randomUUID();
        this.number = Objects.requireNonNull(number);
        this.seatClass = Objects.requireNonNull(seatClass);
    }

    public UUID id() { return id; }

    public String number(){ return number; }

    public SeatClass seatClass(){ return seatClass; }

    @Override
    public String toString()
    {
        return number + " (" + seatClass + ")";
    }
}

package org.kodigo.checkin.model;

import org.kodigo.bookings.model.Booking;
import org.kodigo.passengers.model.Passenger;

import java.util.UUID;

public final class BoardingPass {
    private final UUID id;
    private final String code;
    private final Booking booking;

    public BoardingPass(String code, Booking booking) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.booking = booking;
    }

    public UUID id() {
        return id;
    }

    public Booking booking() {
        return booking;
    }

    public String code() {
        return code;
    }
}
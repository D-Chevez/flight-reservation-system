package org.kodigo.checkin.model;

import org.kodigo.bookings.model.Booking;
import java.time.LocalDateTime;
import java.util.UUID;

public final class BoardingPass {
    private final UUID id;
    private final String bookingCode;
    private final String passengerName;
    private final UUID flightId;
    private final String seatNumber;
    private final LocalDateTime issuedAt;

    public BoardingPass(Booking booking) {
        this.id = UUID.randomUUID();
        this.bookingCode = booking.code();
        this.passengerName = booking.passenger().fullName();
        this.flightId = booking.flight().id();
        this.seatNumber = booking.seatNumber();
        this.issuedAt = LocalDateTime.now();
    }

    public UUID id() { return id; }

    public String bookingCode() {return bookingCode; }

    public String passengerName() { return passengerName; }

    public UUID flightId() { return flightId; }

    public String seatNumber() { return seatNumber; }

    public LocalDateTime issuedAt() { return issuedAt; }
}
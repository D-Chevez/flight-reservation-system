package org.kodigo.bookings.service.booking;

import org.kodigo.bookings.model.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    void create(String code, String flightCode, String passengerPassport, String seatNumber);

    Booking getByCode(String code);

    boolean exists(String code);

    List<Booking> list();

    List<Booking> getByFlightCode(String flightCode);

    List<Booking> getByPassengerPassport(String passengerPassport);

    void cancel(String code);
    
    void checkIn(String code);

    void changeSeat(String code, String newSeatNumber);
}

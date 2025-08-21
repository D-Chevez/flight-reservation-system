package org.kodigo.bookings.service.booking;

import org.kodigo.bookings.model.Booking;
import org.kodigo.flights.model.Flight;
import org.kodigo.passengers.model.Passenger;
import org.kodigo.shared.money.Money;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    void create(String flightCode, String passengerPassport, String seatNumber);

    Optional<Booking> getByCode(String code);

    boolean exists(String code);

    List<Booking> getByFlightCode(String flightCode);

    void cancel(String code);
    
    void checkIn(String code);
}

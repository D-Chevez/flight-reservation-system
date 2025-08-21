package org.kodigo.bookings.repository;

import org.kodigo.bookings.model.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingRepository {
    Booking save(Booking booking);

    Optional<Booking> findByCode(String code);

    List<Booking> list();

    List<Booking> list(Booking.BookingState status);

}

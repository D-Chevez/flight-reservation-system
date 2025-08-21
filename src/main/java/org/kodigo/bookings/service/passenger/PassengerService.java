package org.kodigo.bookings.service.passenger;

import org.kodigo.passengers.model.Passenger;

import java.util.Optional;

public interface PassengerService {
    Optional<Passenger> findByPassport(String passport);
}
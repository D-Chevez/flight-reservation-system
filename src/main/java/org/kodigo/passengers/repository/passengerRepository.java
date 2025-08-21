package org.kodigo.passengers.repository;

import org.kodigo.passengers.model.Passenger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface passengerRepository {

    Passenger save(Passenger passenger);
    Optional<Passenger> findById(UUID id);
    Optional<Passenger> findByPassport(String passport);
    List<Passenger> findAll();
    void delete(UUID id);

}

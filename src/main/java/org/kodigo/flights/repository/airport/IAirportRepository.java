package org.kodigo.flights.repository.airport;

import org.kodigo.flights.model.Airport;

import java.util.List;
import java.util.Optional;

public interface IAirportRepository {
    Optional<Airport> findByCode(String code);

    List<Airport> findAll();

    Airport save(Airport airport);

    void delete(String code);

    boolean exists(String code);
}

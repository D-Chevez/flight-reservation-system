package org.kodigo.flights.service.airport;

import org.kodigo.flights.model.Airport;

import java.util.List;
import java.util.Optional;

public interface IAirportService {
    Airport add(Airport airport);

    Airport update(Airport airport);

    void delete(String code);

    Optional<Airport> getByCode(String code);

    List<Airport> list();
}

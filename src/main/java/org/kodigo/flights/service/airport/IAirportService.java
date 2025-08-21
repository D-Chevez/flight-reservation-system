package org.kodigo.flights.service.airport;

import org.kodigo.flights.model.Airport;

import java.util.List;
import java.util.Optional;

public interface IAirportService {
    Airport create(String name, String city, String country);

    Airport update(String code, Airport airport);

    void delete(String code);

    Optional<Airport> getByCode(String code);

    List<Airport> list();
}

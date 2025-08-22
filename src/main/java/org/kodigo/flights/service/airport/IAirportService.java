package org.kodigo.flights.service.airport;

import org.kodigo.flights.model.Airport;

import java.util.List;
import java.util.Optional;

public interface IAirportService {
    Airport create(String code, String name, String city, String country);

    Airport update(String code, String name, String city, String country);

    void delete(String code);

    Airport getByCode(String code);

    List<Airport> list();
}

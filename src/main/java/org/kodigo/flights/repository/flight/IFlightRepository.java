package org.kodigo.flights.repository.flight;

import org.kodigo.flights.model.Flight;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IFlightRepository {
    Flight save(Flight flight);

    Optional<Flight> findByCode(String code);

    List<Flight> list();

    List<Flight> list(String origin, String destination, LocalDate date);

    void update(Flight flight);

    void saveAll(Collection<Flight> flights);

    void delete(Flight flight);
}

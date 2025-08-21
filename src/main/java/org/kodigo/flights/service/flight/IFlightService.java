package org.kodigo.flights.service.flight;

import org.kodigo.flights.model.Flight;
import org.kodigo.flights.model.SeatMap;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IFlightService {
    Flight create(String originAirportCode, String destinationAirportCode, LocalDate date, Object baseFare);

    List<Flight> search(String origin, String destination, LocalDate date);

    Optional<Flight> getByCode(String code);

    List<String> availableSeats(String code);

    void updateSeatMap(String flightCode, SeatMap seatMap);

    void seed(Collection<Flight> flights);

    List<Flight> getAll();
}

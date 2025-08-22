package org.kodigo.flights.service.flight;

import org.kodigo.flights.model.Flight;
import org.kodigo.flights.model.SeatMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IFlightService {
    Flight create(String code, String originAirportCode, String destinationAirportCode, List<String> seats, LocalDate date, BigDecimal baseFare);

    List<Flight> search(String origin, String destination, LocalDate date);

    Flight getByCode(String code);

    List<Flight> getByAirportCode(String airportCode);

    List<String> availableSeats(String code);

    void updateSeatMap(String flightCode, SeatMap seatMap);

    void seed(Collection<Flight> flights);

    List<Flight> getAll();

    void delete(String flightCode);

    void update(String flightCode, String newOriginAirportCode, String newDestinationAirportCode);
}

package org.kodigo.flights.service.flight;

import org.kodigo.flights.model.Flight;
import org.kodigo.flights.model.SeatMap;
import org.kodigo.flights.repository.flight.IFlightRepository;
import org.kodigo.flights.repository.flight.InMemoryFlightRepository;
import org.kodigo.flights.service.flight.validation.OriginAirportExistsValidator;
import org.kodigo.flights.service.airport.InMemoryAirportService;
import org.kodigo.flights.service.flight.validation.DestinationAirportExistsValidator;
import org.kodigo.flights.service.flight.validation.FlightCreationContext;
import org.kodigo.flights.service.flight.validation.FlightValidator;
import org.kodigo.flights.service.flight.validation.OriginDestinationDifferentValidator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class InMemoryFlightService implements IFlightService {
    private final IFlightRepository repo;
    private final FlightValidator creationChain;

    private final InMemoryAirportService airports;

    public InMemoryFlightService(InMemoryFlightRepository repo, InMemoryAirportService airports){
        this.repo = repo;

        this.airports = airports;

        var v1 = new OriginAirportExistsValidator(airports);
        var v2 = new DestinationAirportExistsValidator(airports);
        var v3 = new OriginDestinationDifferentValidator();
        v1.linkWith(v2).linkWith(v3);
        this.creationChain = v1;
    }

    @Override
    public Flight create(String code, String originCode, String destinationCode, LocalDate date, Object baseFare, SeatMap seatMap) {
        var ctx = new FlightCreationContext(code.toUpperCase(), originCode.toUpperCase(), destinationCode.toUpperCase(), date);
        creationChain.validate(ctx);

        var origin = airports.getByCode(originCode).orElseThrow();
        var dest   = airports.getByCode(destinationCode).orElseThrow();

        var flight = new Flight(code.toUpperCase(), origin, dest, date, seatMap, null);
        repo.save(flight);
        return flight;
    }

    @Override
    public List<Flight> search(String origin, String destination, LocalDate date) {
        return repo.list(origin, destination, date);
    }

    @Override
    public Optional<Flight> getByCode(String code) {
        return repo.findByCode(code);
    }

    @Override
    public List<String> availableSeats(String code) {
         return repo.findByCode(code)
                .map(f -> f.seatMap().freeSeats())
                .orElseGet(List::of);
    }

    @Override
    public void updateSeatMap(String flightCode, SeatMap seatMap) {
        var flight = repo.findByCode(flightCode).orElseThrow();
        var updatedFlight = new Flight(
                flight.code(),
                flight.origin(),
                flight.destination(),
                flight.date(),
                seatMap,
                flight.baseFare()
        );
        repo.update(flight);
    }

    @Override
    public void seed(Collection<Flight> flights) {
        repo.saveAll(flights);
    }
}

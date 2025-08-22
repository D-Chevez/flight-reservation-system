package org.kodigo.flights.service.flight;

import org.kodigo.flights.model.Flight;
import org.kodigo.flights.model.Seat;
import org.kodigo.flights.model.SeatMap;
import org.kodigo.flights.repository.flight.IFlightRepository;
import org.kodigo.flights.service.airport.IAirportService;
import org.kodigo.flights.service.flight.validation.*;
import org.kodigo.shared.codegen.ICodeGenerator;
import org.kodigo.shared.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryFlightService implements IFlightService {
    private final IFlightRepository repo;
    private final IAirportService airports;

    private final FlightValidator creationChain;
    private final ICodeGenerator codeGen;


    public InMemoryFlightService(IFlightRepository repo, IAirportService airports, ICodeGenerator codeGen) {
        this.repo = Objects.requireNonNull(repo);
        this.airports = Objects.requireNonNull(airports);
        this.codeGen =  Objects.requireNonNull(codeGen);

        var onCreate = new OriginDestinationDifferentValidator();
        var onCreate2 = new DateFlightValidator();
        var onCreate3 = new FlightCodeUniquenessValidator(repo);
        onCreate.linkWith(onCreate2);
        this.creationChain = onCreate;
    }

    @Override
    public Flight create(String code, String originAirportCode, String destinationAirportCode, List<String> seats, LocalDate date, BigDecimal baseFare) {
        //String code = codeGen.nextCode();

        // Valida las condiciones de creacion
        var ctx = new FlightCreationContext(code, originAirportCode, destinationAirportCode, date);
        creationChain.validate(ctx);

        var origin = airports.getByCode(originAirportCode);
        var dest   = airports.getByCode(destinationAirportCode);

        List<Seat> seatList = seats.stream()
                .map(num -> new Seat(num, Seat.SeatClass.ECONOMY))
                .toList();

        SeatMap seatMap = new SeatMap(seatList);

        Money baseFareMoney = Money.of(baseFare, "USD");

        var flight = new Flight(code, origin, dest, date, seatMap, baseFareMoney);
        repo.save(flight);
        return flight;
    }

    @Override
    public List<Flight> search(String origin, String destination, LocalDate date) {
        return repo.list(origin, destination, date);
    }

    @Override
    public Flight getByCode(String code) {
        var optFlight = repo.findByCode(code);

        if (optFlight.isEmpty()) throw new IllegalArgumentException("Flight '" + code + "' not found");

        return optFlight.get();
    }

    @Override
    public List<Flight> getByAirportCode(String airportCode) {
        return repo.list().stream()
                .filter(b -> b.origin().code().equals(airportCode))
                .toList();
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

    @Override
    public List<Flight> getAll() {
        return repo.list();
    }

    @Override
    public void delete(String flightCode) {
        var flight = repo.findByCode(flightCode).orElseThrow();
        repo.delete(flight);
    }

    //TODO Implementa validator
    @Override
    public void update(String flightCode, String newOriginAirportCode, String newDestinationAirportCode) {
        var flight = repo.findByCode(flightCode).orElseThrow();
        var newOrigin = airports.getByCode(newOriginAirportCode);
        var newDestination = airports.getByCode(newDestinationAirportCode);
        var updatedFlight = new Flight(
                flight.code(),
                newOrigin,
                newDestination,
                flight.date(),
                flight.seatMap(),
                flight.baseFare()
        );
        repo.update(updatedFlight);
    }
}

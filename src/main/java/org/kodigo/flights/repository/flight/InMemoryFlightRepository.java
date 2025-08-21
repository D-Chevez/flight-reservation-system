package org.kodigo.flights.repository.flight;

import org.kodigo.flights.model.Flight;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class InMemoryFlightRepository implements IFlightRepository {
    private final Map<String, Flight> data = new ConcurrentHashMap<>();

    @Override
    public Flight save(Flight flight) {
        data.put(flight.code(), flight);
        return flight;
    }

    @Override
    public Optional<Flight> findByCode(String code) {
        return Optional.ofNullable(data.get(code));
    }

    @Override
    public List<Flight> list(String origin, String destination, LocalDate date) {
        return data.values()
                .stream()
                .filter(
                        f -> f.origin().toString().equalsIgnoreCase(origin)
                                && f.destination().toString().equalsIgnoreCase(destination)
                                && f.date().equals(date)
                )
                .collect(Collectors.toList());
    }

    @Override
    public void update(Flight flight) {
        data.put(flight.code(), flight);
    }

    @Override
    public void saveAll(Collection<Flight> flights) {
        for (var f : flights) data.put(f.code(), f);
    }
}

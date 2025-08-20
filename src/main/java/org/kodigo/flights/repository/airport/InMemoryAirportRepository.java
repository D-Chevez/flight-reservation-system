package org.kodigo.flights.repository.airport;

import org.kodigo.flights.model.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryAirportRepository implements IAirportRepository {
    private final Map<String, Airport> data = new ConcurrentHashMap<>();

    @Override
    public Optional<Airport> findByCode(String code) {
        return Optional.ofNullable(data.get(code));
    }

    @Override
    public List<Airport> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void save(Airport airport) {
        data.put(airport.code(), airport);
    }

    @Override
    public void update(Airport airport) {
        data.put(airport.code(), airport);
    }

    @Override
    public void delete(String code) {
        data.remove(code);
    }

    @Override
    public boolean exists(String code) {
        return data.containsKey(code);
    }
}

package org.kodigo.flights.service.airport;

import org.kodigo.flights.model.Airport;
import org.kodigo.flights.repository.airport.InMemoryAirportRepository;
import org.kodigo.flights.service.airport.validation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryAirportService implements IAirportService {
    private final InMemoryAirportRepository repo;

    // Chains
    private final AirportValidator onCreate;
    private final AirportValidator onUpdate;

    public InMemoryAirportService(InMemoryAirportRepository repo){
        this.repo = repo;

        // Create chain: format -> name -> uniqueness
        var v1 = new AirportCodeFormatValidator();
        var v2 = new AirportNameRequiredValidator();
        var v3 = new AirportUniquenessValidator(repo);
        v1.linkWith(v2).linkWith(v3);
        this.onCreate = v1;

        // Update chain: format -> name (ya existe lo validamos adentro del método)
        var u1 = new AirportCodeFormatValidator();
        var u2 = new AirportNameRequiredValidator();
        u1.linkWith(u2);
        this.onUpdate = u1;
    }

    private Airport normalize(Airport a){
        return new Airport(normalizeCode(a.code()), a.name().trim(), a.city(), a.country());
    }

    private String normalizeCode(String code){
        return Objects.requireNonNull(code, "code").trim().toUpperCase();
    }

    @Override
    public Airport add(Airport airport) {
        var normalized = normalize(airport);
        onCreate.validate(new AirportValidationContext(normalized.code(), normalized.name()));
        repo.save(normalized);
        return normalized;
    }

    @Override
    public Airport update(Airport airport) {
        var normalized = normalize(airport);

        if (!repo.exists(normalized.code()))
            throw new NoSuchElementException("Airport not found: " + normalized.code());

        onUpdate.validate(new AirportValidationContext(normalized.code(), normalized.name()));
        repo.update(normalized);
        return normalized;
    }

    @Override
    public void delete(String code) {
        var c = normalizeCode(code);
        //! Verificar si hay vuelos que lo referencian (Opcional)
        repo.delete(c);
    }

    @Override
    public Optional<Airport> getByCode(String code) {
        return repo.findByCode(code);
    }

    @Override
    public List<Airport> list() {
        return repo.findAll();
    }
}

package org.kodigo.flights.service.airport;

import org.kodigo.flights.model.Airport;
import org.kodigo.flights.repository.airport.IAirportRepository;
import org.kodigo.flights.repository.airport.InMemoryAirportRepository;
import org.kodigo.flights.service.airport.validation.*;
import org.kodigo.shared.codegen.CodeGenerator;
import org.kodigo.shared.codegen.ICodeGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryAirportService implements IAirportService {
    private final IAirportRepository repo;
    private final AirportValidator onCreate;
    private final AirportValidator onUpdate;
    private final ICodeGenerator codeGenerator;

    public InMemoryAirportService(IAirportRepository repo,
                                  AirportValidator CreateValidator,
                                  AirportValidator UpdateValidator,
                                  ICodeGenerator codeGenerator) {
        this.repo = Objects.requireNonNull(repo);
        this.onCreate = Objects.requireNonNull(CreateValidator);
        this.onUpdate = Objects.requireNonNull(UpdateValidator);
        this.codeGenerator = Objects.requireNonNull(codeGenerator);
    }

    @Override
    public Airport create(String name, String city, String country) {
        String code = codeGenerator.nextCode();

        onCreate.validate(new AirportValidationContext(code, name));
        return repo.save(new Airport(code, name, city, country));
    }

    @Override
    public Airport update(String code, Airport airport) {
        onUpdate.validate(new AirportValidationContext(code, airport.name()));
        return repo.save(airport);
    }

    @Override
    public void delete(String code) {
        //! Verificar si hay vuelos que lo referencian (Opcional)
        repo.delete(code);
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

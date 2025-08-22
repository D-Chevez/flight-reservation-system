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
    private final AirportValidator createValidator;
    private final AirportValidator updateValidator;
    private final ICodeGenerator codeGenerator;

    public InMemoryAirportService(IAirportRepository repo,
                                  AirportValidator createValidator,
                                  AirportValidator updateValidator,
                                  ICodeGenerator codeGenerator) {
        this.repo = Objects.requireNonNull(repo);
        this.createValidator = Objects.requireNonNull(createValidator);
        this.updateValidator = Objects.requireNonNull(updateValidator);
        this.codeGenerator = Objects.requireNonNull(codeGenerator);
    }

    @Override
    public Airport create(String code, String name, String city, String country) {
        //String code = codeGenerator.nextCode();
        createValidator.validate(new AirportValidationContext(code, name, city, country));
        return repo.save(new Airport(code, name, city, country));
    }

    @Override
    public Airport update(String code, String name, String city, String country) {
        updateValidator.validate(new AirportValidationContext(code, name, city, country));
        Airport updated = new Airport(code, name, city, country);
        return repo.save(updated);
    }

    @Override
    public void delete(String code) {
        //! Verificar si hay vuelos que lo referencian (Opcional)
        repo.delete(code);
    }

    @Override
    public Airport getByCode(String code) {
        var optAirport = repo.findByCode(code);

        if(optAirport.isEmpty()) throw new IllegalStateException("Airport wiht code '" + code + "' not found.");

        return optAirport.get();
    }

    @Override
    public List<Airport> list() {
        return repo.findAll();
    }
}

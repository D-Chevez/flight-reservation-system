package org.kodigo.flights.service.flight.validation;

import org.kodigo.flights.repository.airport.IAirportRepository;
import org.kodigo.flights.repository.flight.IFlightRepository;
import org.kodigo.flights.service.airport.validation.AirportValidationContext;
import org.kodigo.flights.service.airport.validation.BaseAirportValidator;

public final class FlightCodeUniquenessValidator extends BaseAirportValidator {
    private final IFlightRepository repo;

    public FlightCodeUniquenessValidator(IFlightRepository repo){ this.repo = repo; }

    @Override
    public void validate(AirportValidationContext ctx){
        var optAirport = repo.findByCode(ctx.code());
        if (optAirport.isPresent())
            throw new IllegalStateException("Code '" + ctx.code() + "' already used.");
        checkNext(ctx);
    }
}
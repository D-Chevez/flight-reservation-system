package org.kodigo.flights.service.airport.validation;

import org.kodigo.flights.repository.airport.IAirportRepository;

public final class AirportRequiredExistValidator extends BaseAirportValidator {
    private final IAirportRepository repo;

    public AirportRequiredExistValidator(IAirportRepository repo){ this.repo = repo; }

    @Override
    public void validate(AirportValidationContext ctx){
        var optAirport = repo.findByCode(ctx.code());
        if (optAirport.isEmpty())
            throw new IllegalStateException("Airport wiht code '" + ctx.code() + "' not found.");
        checkNext(ctx);
    }
}
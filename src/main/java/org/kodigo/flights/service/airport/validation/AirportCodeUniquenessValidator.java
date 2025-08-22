package org.kodigo.flights.service.airport.validation;

import org.kodigo.flights.repository.airport.IAirportRepository;

public final class AirportCodeUniquenessValidator extends BaseAirportValidator {
    private final IAirportRepository repo;

    public AirportCodeUniquenessValidator(IAirportRepository repo){ this.repo = repo; }

    @Override
    public void validate(AirportValidationContext ctx){
        var optAirport = repo.findByCode(ctx.code());
        if (optAirport.isPresent())
            throw new IllegalStateException("Code '" + ctx.code() + "' already used.");
        checkNext(ctx);
    }
}
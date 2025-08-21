package org.kodigo.flights.service.airport.validation;

import org.kodigo.flights.repository.airport.IAirportRepository;

public final class AirportUniquenessValidator extends BaseAirportValidator {
    private final IAirportRepository repo;

    public AirportUniquenessValidator(IAirportRepository repo){ this.repo = repo; }

    @Override
    public void validate(AirportValidationContext ctx){
        if (repo.exists(ctx.code()))
            throw new IllegalStateException("Airport already exists: " + ctx.code());
        checkNext(ctx);
    }
}
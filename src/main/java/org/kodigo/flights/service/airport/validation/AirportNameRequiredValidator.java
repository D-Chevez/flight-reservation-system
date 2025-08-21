package org.kodigo.flights.service.airport.validation;

public final class AirportNameRequiredValidator extends BaseAirportValidator {
    @Override
    public void validate(AirportValidationContext ctx){
        var name = ctx.name();
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Airport name is required.");
        checkNext(ctx);
    }
}

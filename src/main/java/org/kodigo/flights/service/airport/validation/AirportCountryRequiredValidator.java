package org.kodigo.flights.service.airport.validation;

public final class AirportCountryRequiredValidator extends BaseAirportValidator {
    @Override
    public void validate(AirportValidationContext ctx){
        var country = ctx.country();
        if (country == null || country.isBlank())
            throw new IllegalArgumentException("Airport country is required.");
        checkNext(ctx);
    }
}

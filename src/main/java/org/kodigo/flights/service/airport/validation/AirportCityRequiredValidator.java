package org.kodigo.flights.service.airport.validation;

public final class AirportCityRequiredValidator extends BaseAirportValidator {
    @Override
    public void validate(AirportValidationContext ctx){
        var city = ctx.city();
        if (city == null || city.isBlank())
            throw new IllegalArgumentException("Airport city is required.");
        checkNext(ctx);
    }
}

package org.kodigo.flights.service.airport.validation;


public final class AirportCodeFormatValidator extends BaseAirportValidator {
    @Override
    public void validate(AirportValidationContext ctx){
        var code = ctx.code();
        if (code == null || !code.matches("[A-Z]{3}"))
            throw new IllegalArgumentException("Airport code must be 3 uppercase letters (IATA).");
        checkNext(ctx);
    }
}

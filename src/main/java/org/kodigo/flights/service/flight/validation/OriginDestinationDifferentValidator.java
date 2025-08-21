package org.kodigo.flights.service.flight.validation;

public final class OriginDestinationDifferentValidator extends BaseFlightValidator {
    @Override
    public void validate(FlightCreationContext ctx){
        if (ctx.originCode().equals(ctx.destinationCode()))
            throw new IllegalArgumentException("Origin and destination must be different.");
        checkNext(ctx);
    }
}
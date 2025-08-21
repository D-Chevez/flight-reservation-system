package org.kodigo.flights.service.flight.validation;


import org.kodigo.flights.service.airport.IAirportService;

public final class OriginAirportExistsValidator extends BaseFlightValidator {
    private final IAirportService airports;
    public OriginAirportExistsValidator(IAirportService airports){ this.airports = airports; }

    @Override
    public void validate(FlightCreationContext ctx){
        var exists = airports.getByCode(ctx.originCode()).isPresent();
        if (!exists) throw new IllegalArgumentException("Origin airport does not exist: " + ctx.originCode());
        checkNext(ctx);
    }
}
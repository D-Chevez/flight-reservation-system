package org.kodigo.flights.service.flight.validation;

import org.kodigo.flights.service.airport.IAirportService;

public final class DestinationAirportExistsValidator extends BaseFlightValidator {
    private final IAirportService airports;

    public DestinationAirportExistsValidator(IAirportService airports){ this.airports = airports; }

    @Override
    public void validate(FlightCreationContext ctx){
        var exists = airports.getByCode(ctx.destinationCode()).isPresent();
        if (!exists) throw new IllegalArgumentException("Destination airport does not exist: " + ctx.destinationCode());
        checkNext(ctx);
    }
}
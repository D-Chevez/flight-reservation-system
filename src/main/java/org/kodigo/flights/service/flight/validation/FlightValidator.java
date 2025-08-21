package org.kodigo.flights.service.flight.validation;


public interface FlightValidator {
    void validate(FlightCreationContext ctx);

    FlightValidator linkWith(FlightValidator next);
}
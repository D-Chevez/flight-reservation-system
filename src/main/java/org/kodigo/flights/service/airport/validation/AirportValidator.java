package org.kodigo.flights.service.airport.validation;

public interface AirportValidator {
    void validate(AirportValidationContext ctx);

    AirportValidator linkWith(AirportValidator next);
}
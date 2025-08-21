package org.kodigo.flights.service.flight.validation;

import java.time.LocalDate;

public record FlightCreationContext(
        String flightCode,
        String originCode,
        String destinationCode,
        LocalDate date
) { }

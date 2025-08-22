package org.kodigo.flights.service.flight.validation;

import org.kodigo.flights.service.airport.IAirportService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateFlightValidator extends BaseFlightValidator{
    @Override
    public void validate(FlightCreationContext ctx) {
        LocalDate minDate = LocalDate.now().plusDays(5);
        if (ctx.date().isBefore(minDate)) {
            throw new IllegalArgumentException("La fecha del vuelo debe ser al menos 5 días posterior a la fecha actual.");
        }
    }
}

package org.kodigo.flights.service.flight.validation;

public abstract class BaseFlightValidator implements FlightValidator {
    private FlightValidator next;

    @Override
    public FlightValidator linkWith(FlightValidator next){ this.next = next; return next; }

    protected void checkNext(FlightCreationContext ctx){ if (next != null) next.validate(ctx); }
}

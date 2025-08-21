package org.kodigo.flights.service.airport.validation;


public abstract class BaseAirportValidator implements AirportValidator {
    private AirportValidator next;

    @Override
    public AirportValidator linkWith(AirportValidator next){ this.next = next; return next; }

    protected void checkNext(AirportValidationContext ctx){ if (next != null) next.validate(ctx); }
}
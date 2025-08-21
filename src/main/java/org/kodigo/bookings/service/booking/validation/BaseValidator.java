package org.kodigo.bookings.service.booking.validation;

public abstract class BaseValidator implements BookingValidator {
    private BookingValidator next;
    @Override public BookingValidator linkWith(BookingValidator next){ this.next = next; return next; }
    protected void checkNext(BookingValidationContext ctx){ if (next != null) next.validate(ctx); }
}
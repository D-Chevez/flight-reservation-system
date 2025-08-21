package org.kodigo.bookings.service.booking.validation;

public interface BookingValidator {
    void validate(BookingValidationContext ctx);
    BookingValidator linkWith(BookingValidator next);
}
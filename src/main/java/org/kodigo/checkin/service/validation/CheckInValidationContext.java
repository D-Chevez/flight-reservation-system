package org.kodigo.checkin.service.validation;

public record CheckInValidationContext(
        String bookingCode,
        String passengerPassport,
        String requestedSeat
) { }
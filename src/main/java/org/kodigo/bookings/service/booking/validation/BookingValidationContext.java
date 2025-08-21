package org.kodigo.bookings.service.booking.validation;

public record BookingValidationContext(String passengerPassport, String flightCode, String requestedSeat) { }
package org.kodigo.bookings.service.booking;

import org.kodigo.bookings.repository.IBookingRepository;
import org.kodigo.bookings.repository.InMemoryBookingRepository;
import org.kodigo.shared.codegen.CodeGenerator;
import org.kodigo.shared.codegen.ICodeGenerator;
import org.kodigo.bookings.service.booking.validation.FlightSeatValidator;
import org.kodigo.bookings.service.booking.validation.PassengerActiveValidator;
import org.kodigo.bookings.service.passenger.PassengerService;
import org.kodigo.bookings.service.pricing.DecoratorPricingService;
import org.kodigo.bookings.service.pricing.IPricingService;
import org.kodigo.bookings.service.seat.FirstFreeSeatPolicy;
import org.kodigo.bookings.service.seat.SeatAssignmentPolicy;
import org.kodigo.flights.service.flight.IFlightService;

import java.math.BigDecimal;

public final class BookingServiceFactory {
    public static IBookingService build(IFlightService flights, PassengerService passengers) {
        IBookingRepository bookingRepo = new InMemoryBookingRepository();

        // Chain of Responsibility
        var v1 = new PassengerActiveValidator(passengers);
        var v2 = new FlightSeatValidator(flights);
        v1.linkWith(v2);

        SeatAssignmentPolicy seatPolicy = new FirstFreeSeatPolicy();

        IPricingService pricing = new DecoratorPricingService(new BigDecimal("0.13")); // 13% VAT

        var exists = (java.util.function.Predicate<String>) code -> bookingRepo.findByCode(code).isPresent();
        ICodeGenerator codeGen = new CodeGenerator(5, "FLT", "-", exists, 9999);

        return new InMemoryBookingService(
                bookingRepo, flights, passengers, v1, seatPolicy, pricing, codeGen
        );
    }
}
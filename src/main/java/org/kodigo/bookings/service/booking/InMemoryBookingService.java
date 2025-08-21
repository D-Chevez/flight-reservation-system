package org.kodigo.bookings.service.booking;


import org.kodigo.bookings.model.Booking;
import org.kodigo.bookings.repository.IBookingRepository;
import org.kodigo.shared.codegen.ICodeGenerator;
import org.kodigo.bookings.service.booking.validation.BookingValidationContext;
import org.kodigo.bookings.service.booking.validation.BookingValidator;
import org.kodigo.bookings.service.passenger.PassengerService;
import org.kodigo.bookings.service.pricing.IPricingService;
import org.kodigo.bookings.service.seat.SeatAssignmentPolicy;
import org.kodigo.flights.service.flight.IFlightService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryBookingService implements IBookingService{
    private final IBookingRepository bookingRepo;
    private final IFlightService flights;
    private final PassengerService passengers;
    private final BookingValidator validatorChain;
    private final SeatAssignmentPolicy seatPolicy;
    private final IPricingService pricing;
    private final ICodeGenerator codeGen;

    public InMemoryBookingService(IBookingRepository bookingRepo,
                                  IFlightService flights,
                                  PassengerService passengers,
                                  BookingValidator validatorChain,
                                  SeatAssignmentPolicy seatPolicy,
                                  IPricingService pricing,
                                  ICodeGenerator codeGen) {
        this.bookingRepo = Objects.requireNonNull(bookingRepo);
        this.flights = Objects.requireNonNull(flights);
        this.passengers = Objects.requireNonNull(passengers);
        this.validatorChain = Objects.requireNonNull(validatorChain);
        this.seatPolicy = Objects.requireNonNull(seatPolicy);
        this.pricing = Objects.requireNonNull(pricing);
        this.codeGen = Objects.requireNonNull(codeGen);
    }

    @Override
    public void create(String passengerPassport, String flightCode, String requestedSeat) {
        // 1) validations
        validatorChain.validate(new BookingValidationContext(passengerPassport, flightCode, requestedSeat));

        // 2) load aggregates
        var flight = flights.getByCode(flightCode).orElseThrow();
        var passenger = passengers.findByPassport(passengerPassport).orElseThrow();

        // 3) seat assignment + occupy
        var seatNumber = seatPolicy.selectSeat(flight, requestedSeat);
        flight.seatMap().occupy(seatNumber);

        // 4) price
        var total = pricing.calculateTotal(flight.baseFare());

        // 5) build + persist
        var code = codeGen.nextCode();
        var booking = new Booking(code, flight, passenger, seatNumber, total);
        booking.confirm();

        bookingRepo.save(booking);
        flights.updateSeatMap(flight.code(), flight.seatMap());
    }

    @Override
    public Optional<Booking> getByCode(String code) {
        return bookingRepo.findByCode(code);
    }

    @Override
    public boolean exists(String code) {
        return bookingRepo.findByCode(code).isPresent();
    }

    @Override
    public List<Booking> getByFlightCode(String flightCode) {
        return bookingRepo.list().stream().filter(b -> b.flight().code().equals(flightCode))
                .toList();
    }


    @Override
    public void cancel(String code) {
        var objectBooking = getByCode(code);

        if(objectBooking.isEmpty()) throw new IllegalArgumentException("Booking not found: " + code);

        var booking = objectBooking.get();
        if (booking.state() == Booking.BookingState.CANCELLED) return;

        var flight = booking.flight();
        flight.seatMap().release(booking.seatNumber());

        booking.cancel();
        bookingRepo.save(booking);
        flights.updateSeatMap(flight.code(), flight.seatMap());
    }

    @Override
    public void checkIn(String code) {
        var objectBooking = getByCode(code);

        if(objectBooking.isEmpty()) throw new IllegalArgumentException("Booking not found: " + code);

        var booking = objectBooking.get();
        if (booking.state() == Booking.BookingState.CHECKED_IN) return;

        if (booking.state() == Booking.BookingState.CANCELLED) {
            throw new IllegalStateException("Cannot check-in a cancelled booking: " + code);
        }

        booking.checkIn();
        bookingRepo.save(booking);
        flights.updateSeatMap(booking.flight().code(), booking.flight().seatMap());
    }


}

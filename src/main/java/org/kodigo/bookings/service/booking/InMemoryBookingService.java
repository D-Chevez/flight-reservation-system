package org.kodigo.bookings.service.booking;


import org.kodigo.bookings.model.Booking;
import org.kodigo.bookings.repository.IBookingRepository;
import org.kodigo.checkin.service.ICheckInService;
import org.kodigo.shared.codegen.ICodeGenerator;
import org.kodigo.bookings.service.booking.validation.BookingValidationContext;
import org.kodigo.bookings.service.booking.validation.BookingValidator;
import org.kodigo.passengers.service.PassengerService;
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
    public void create(String code, String passengerPassport, String flightCode, String requestedSeat) {
        validatorChain.validate(new BookingValidationContext(passengerPassport, flightCode, requestedSeat));

        var flight = flights.getByCode(flightCode);
        var passenger = passengers.findByPassport(passengerPassport);

        var seatNumber = seatPolicy.selectSeat(flight, requestedSeat);
        flight.seatMap().occupy(seatNumber);

        var total = pricing.calculateTotal(flight.baseFare());

        // var code = codeGen.nextCode();
        var booking = new Booking(code, flight, passenger, seatNumber, total);
        booking.confirm();

        bookingRepo.save(booking);
        flights.updateSeatMap(flight.code(), flight.seatMap());
    }

    @Override
    public Booking getByCode(String code) {
        var opt = bookingRepo.findByCode(code);

        if (opt.isEmpty()) throw new IllegalArgumentException("Booking '" +code+ "' not found.");

        return opt.get();
    }

    @Override
    public boolean exists(String code) {
        return bookingRepo.findByCode(code).isPresent();
    }

    @Override
    public List<Booking> list() {
        return bookingRepo.list();
    }

    @Override
    public List<Booking> getByFlightCode(String flightCode) {
        return bookingRepo.list().stream().filter(b -> b.flight().code().equals(flightCode))
                .toList();
    }

    @Override
    public List<Booking> getByPassengerPassport(String passengerPassport) {
        return bookingRepo.list().stream()
                .filter(b -> b.passenger().passport().equals(passengerPassport))
                .toList();
    }


    @Override
    public void cancel(String code) {
        var booking = getByCode(code);

        if (booking.state() == Booking.BookingState.CANCELLED) return;

        var flight = booking.flight();
        flight.seatMap().release(booking.seatNumber());

        booking.cancel();
        bookingRepo.save(booking);
        flights.updateSeatMap(flight.code(), flight.seatMap());
    }

    @Override
    public void checkIn(String code) {
        var booking = getByCode(code);

        if (booking.state() == Booking.BookingState.CHECKED_IN) return;

        if (booking.state() == Booking.BookingState.CANCELLED) {
            throw new IllegalStateException("Cannot check-in a cancelled booking: " + code);
        }

        booking.checkIn();
        bookingRepo.save(booking);
        flights.updateSeatMap(booking.flight().code(), booking.flight().seatMap());
    }


    @Override
    public void changeSeat(String code, String newSeatNumber) {
        var booking = getByCode(code);

        if (booking.state() == Booking.BookingState.CANCELLED) {
            throw new IllegalStateException("Cannot change seat of a cancelled booking: " + code);
        }

        var flight = booking.flight();
        flight.seatMap().release(booking.seatNumber());

        var seatNumber = seatPolicy.selectSeat(flight, newSeatNumber);
        flight.seatMap().occupy(seatNumber);

        Booking newBooking = new Booking(
                booking.code(),
                flight,
                booking.passenger(),
                seatNumber,
                booking.total()
        );
        bookingRepo.save(newBooking);
        flights.updateSeatMap(flight.code(), flight.seatMap());
    }

}

package org.kodigo.checkin.service;

import org.kodigo.bookings.model.Booking;
import org.kodigo.bookings.service.booking.IBookingService;
import org.kodigo.checkin.model.BoardingPass;
import org.kodigo.checkin.repository.IBoardingPassRepository;
import org.kodigo.checkin.service.validation.*;
import org.kodigo.shared.codegen.ICodeGenerator;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryCheckInService implements ICheckInService {
    private final IBoardingPassRepository repo;
    private final IBookingService bookings;
    private final ICodeGenerator codegen;
    private final CheckInValidator validatorChain;

    public InMemoryCheckInService(IBoardingPassRepository repo,
                                  IBookingService bookings,
                                  ICodeGenerator codegen,
                                  CheckInValidator validatorChain) {
        this.repo = Objects.requireNonNull(repo);
        this.bookings = Objects.requireNonNull(bookings);
        this.codegen = Objects.requireNonNull(codegen);
        this.validatorChain = Objects.requireNonNull(validatorChain);
    }

    @Override
    public BoardingPass checkIn(String bookingCode, String passengerPassport, String requestedSeat) {
        var ctx = new CheckInValidationContext(bookingCode, passengerPassport, requestedSeat);
        validatorChain.validate(ctx);

        Booking b = bookings.getByCode(bookingCode).orElseThrow();

        String bpCode = codegen.nextCode();
        BoardingPass bp = new BoardingPass(
                bpCode,
                b
        );

        repo.save(bp);
        return bp;
    }

    @Override
    public Optional<BoardingPass> getByBoardingPassCode(String bpCode) {
        return repo.findByCode(bpCode);
    }

    @Override
    public Optional<BoardingPass> getByBookingCode(String bookingCode) {
        return repo.findByBookingCode(bookingCode);
    }

    @Override
    public void undo(String bookingCode) {
        repo.deleteByBookingCode(bookingCode);
    }
}
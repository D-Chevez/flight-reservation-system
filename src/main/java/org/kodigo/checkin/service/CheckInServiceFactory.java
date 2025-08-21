package org.kodigo.checkin.service;


import org.kodigo.bookings.service.booking.IBookingService;
import org.kodigo.checkin.repository.IBoardingPassRepository;
import org.kodigo.checkin.repository.InMemoryBoardingPassRepository;
import org.kodigo.checkin.service.validation.*;
import org.kodigo.shared.codegen.ICodeGenerator;

import java.time.Clock;
import java.time.Duration;

public final class CheckInServiceFactory {

    public static ICheckInService build(IBookingService bookings, ICodeGenerator codegen) {
        IBoardingPassRepository repo = new InMemoryBoardingPassRepository();

        var v1 = new BookingConfirmedValidator(bookings);
        var v2 = new PassengerMatchesBookingValidator(bookings);
        var v3 = new FlightCheckInWindowValidator(bookings, Duration.ofHours(48), Duration.ofHours(1), Clock.systemUTC());
        var v4 = new NotAlreadyCheckedInValidator(repo);
        var v5 = new SeatChangeAllowedValidator(bookings, false);

        v1.linkWith(v2).linkWith(v3).linkWith(v4).linkWith(v5);

        return new InMemoryCheckInService(repo, bookings, codegen, v1);
    }
}


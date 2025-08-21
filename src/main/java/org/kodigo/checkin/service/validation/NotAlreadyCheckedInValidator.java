package org.kodigo.checkin.service.validation;

import org.kodigo.checkin.repository.IBoardingPassRepository;

public final class NotAlreadyCheckedInValidator extends BaseCheckInValidator {
    private final IBoardingPassRepository repo;

    public NotAlreadyCheckedInValidator(IBoardingPassRepository repo){ this.repo = repo; }

    @Override
    public void validate(CheckInValidationContext ctx){
        if (repo.existsByBookingCode(ctx.bookingCode())) {
            throw new IllegalStateException("Booking already checked-in: " + ctx.bookingCode());
        }
        checkNext(ctx);
    }
}
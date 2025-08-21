package org.kodigo.bookings.service.booking.validation;

//TODO: Migrate to real service
import org.kodigo.bookings.service.passenger.PassengerService;
import org.kodigo.passengers.model.Passenger;

public final class PassengerActiveValidator extends BaseValidator {
    private final PassengerService passengers;
    public PassengerActiveValidator(PassengerService passengers){ this.passengers = passengers; }

    @Override
    public void validate(BookingValidationContext ctx){
        var p = passengers.findByPassport(ctx.passengerPassport())
                .orElseThrow(() -> new IllegalStateException("Passenger not found"));
        if (p.status() != Passenger.Status.ACTIVE) {
            throw new IllegalStateException("Passenger is not active");
        }
        checkNext(ctx);
    }
}


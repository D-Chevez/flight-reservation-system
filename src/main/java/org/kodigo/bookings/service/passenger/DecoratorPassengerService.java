package org.kodigo.bookings.service.passenger;


import org.kodigo.passengers.model.Passenger;
import java.util.Optional;

public class DecoratorPassengerService implements PassengerService {
    protected final PassengerService delegate;

    public DecoratorPassengerService(PassengerService delegate) { this.delegate = delegate; }

    @Override public Optional<Passenger> findByPassport(String passport) {
        // hook: logging/metrics
        return delegate.findByPassport(passport);
    }
}

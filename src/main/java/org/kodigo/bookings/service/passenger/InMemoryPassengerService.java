package org.kodigo.bookings.service.passenger;

import org.kodigo.passengers.model.Passenger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryPassengerService implements PassengerService {
    private final Map<String, Passenger> byPassport = new ConcurrentHashMap<>();

    public void seed(Collection<Passenger> passengers){
        for (var p : passengers) byPassport.put(p.passport(), p);
    }

    @Override
    public Optional<Passenger> findByPassport(String passport) {
        return Optional.ofNullable(byPassport.get(passport));
    }
}

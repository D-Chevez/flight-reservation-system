package org.kodigo.bookings.repository;

import org.kodigo.bookings.model.Booking;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class InMemoryBookingRepository implements IBookingRepository {
    private final Map<String, Booking> data = new ConcurrentHashMap<>(); // code -> booking

    @Override
    public Booking save(Booking booking) {
        return data.put(booking.code(), booking);
    }

    @Override
    public Optional<Booking> findByCode(String code) {
        return Optional.ofNullable(data.get(code));
    }

    @Override
    public List<Booking> list() {
        return List.copyOf(data.values());
    }

    @Override
    public List<Booking> list(Booking.BookingState status) {
        return data.values().stream()
                .filter(booking -> booking.state() == status)
                .collect(Collectors.toList());
    }
}


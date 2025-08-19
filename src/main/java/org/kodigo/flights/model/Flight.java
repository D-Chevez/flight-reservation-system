package org.kodigo.flights.model;

import org.kodigo.shared.money.Money;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class Flight {
    private final UUID id;
    private final String code;
    private final Airport origin;
    private final Airport destination;
    private final LocalDate date;
    private final SeatMap seatMap;
    private final Money baseFare;

    public Flight(String code, Airport origin, Airport destination, LocalDate date, SeatMap seatMap, Money baseFare) {
        this.id = UUID.randomUUID();
        this.code = Objects.requireNonNull(code);
        this.origin = Objects.requireNonNull(origin);
        this.destination = Objects.requireNonNull(destination);
        this.date = Objects.requireNonNull(date);
        this.seatMap = Objects.requireNonNull(seatMap);
        this.baseFare = Objects.requireNonNull(baseFare);
    }

    public UUID id(){ return id; }

    public String code(){ return code; }

    public Airport origin(){ return origin; }

    public Airport destination(){ return destination; }

    public LocalDate date(){ return date; }

    public SeatMap seatMap(){ return seatMap; }

    public Money baseFare(){ return baseFare; }
}

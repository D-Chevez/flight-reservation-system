package org.kodigo.flights.model;

import java.util.Objects;
import java.util.UUID;

public final class Airport {
    private final UUID id;
    private final String code;
    private final String name;
    private final String city;
    private final String country;

    public Airport(String code, String name, String city, String country) {
        this.id = UUID.randomUUID();
        this.code = code.toUpperCase();
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city);
        this.country = Objects.requireNonNull(country);
    }

    public UUID id() { return id; }

    public String code(){ return code; }

    public String name(){ return name; }

    public String city(){ return city; }

    public String country(){ return country; }
}
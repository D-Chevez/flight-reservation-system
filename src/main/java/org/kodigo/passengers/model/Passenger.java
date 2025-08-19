package org.kodigo.passengers.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Passenger {
    public enum SeatClass { ECONOMY, PREMIUM_ECONOMY, BUSINESS }
    public enum Status { ACTIVE, SUSPENDED }

    private final UUID id;
    private final String fullName;
    private final String passport;
    private String email;
    private String phone;
    private Status status;

    public Passenger(String fullName, String passport) {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be null or blank");
        if (passport == null || passport.isBlank()) throw new IllegalArgumentException("Passport cannot be null or blank");

        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.passport = passport;
        this.status = Status.ACTIVE;
    }

    public UUID id() {
        return id;
    }

    public String fullName() {
        return fullName;
    }

    public String passport() {
        return passport;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public Status status() {
        return status;
    }

    public void updateContacts(String email, String phone){
        if(email != null) this.email = email;
        if(phone != null) this.phone = phone;
    }

    public void suspend()
    {
        this.status = Status.SUSPENDED;
    }

    public void reactivate()
    {
        this.status = Status.ACTIVE;
    }
}
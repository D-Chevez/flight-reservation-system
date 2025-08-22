package org.kodigo.checkin.service;

import org.kodigo.checkin.model.BoardingPass;

import java.util.Optional;

public interface ICheckInService {
    BoardingPass checkIn(String bookingCode, String passengerPassport, String requestedSeat);

    BoardingPass getByBookingCode(String bookingCode);
}
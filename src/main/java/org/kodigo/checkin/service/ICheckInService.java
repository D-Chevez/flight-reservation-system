package org.kodigo.checkin.service;

import org.kodigo.checkin.model.BoardingPass;

import java.util.Optional;

public interface ICheckInService {
    BoardingPass checkIn(String bookingCode, String passengerPassport, String requestedSeat);

    Optional<BoardingPass> getByBoardingPassCode(String bpCode);

    Optional<BoardingPass> getByBookingCode(String bookingCode);

    void undo(String bookingCode);
}
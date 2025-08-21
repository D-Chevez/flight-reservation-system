package org.kodigo.checkin.repository;

import org.kodigo.checkin.model.BoardingPass;

import java.util.Optional;

public interface IBoardingPassRepository {
    BoardingPass save(BoardingPass bp);
    Optional<BoardingPass> findByCode(String code);
    Optional<BoardingPass> findByBookingCode(String bookingCode);
    boolean existsByBookingCode(String bookingCode);
    void deleteByBookingCode(String bookingCode);
}

package org.kodigo.checkin.repository;

import org.kodigo.checkin.model.BoardingPass;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryBoardingPassRepository implements IBoardingPassRepository {
    private final Map<String, BoardingPass> byCode = new ConcurrentHashMap<>();
    private final Map<String, String> codeByBooking = new ConcurrentHashMap<>();

    @Override
    public BoardingPass save(BoardingPass bp){
        byCode.put(bp.code(), bp);
        codeByBooking.put(bp.booking().code(), bp.code());
        return bp;
    }

    @Override
    public Optional<BoardingPass> findByCode(String code){
        return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public Optional<BoardingPass> findByBookingCode(String bookingCode){
        var code = codeByBooking.get(bookingCode);
        return code == null ? Optional.empty() : Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsByBookingCode(String bookingCode){
        return codeByBooking.containsKey(bookingCode);
    }

    @Override
    public void deleteByBookingCode(String bookingCode){
        var code = codeByBooking.remove(bookingCode);
        if (code != null) byCode.remove(code);
    }
}
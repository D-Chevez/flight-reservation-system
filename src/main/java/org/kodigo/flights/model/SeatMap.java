package org.kodigo.flights.model;

import java.util.*;

public final class SeatMap {
    private final Map<String, Boolean> occupancy = new HashMap<>(); // seatNumber -> occupied

    public SeatMap(List<Seat> seats) {
        for (Seat s : seats) occupancy.put(s.number(), false);
    }
    public boolean isAvailable(String seatNumber){ return occupancy.containsKey(seatNumber) && !occupancy.get(seatNumber); }

    public void occupy(String seatNumber){
        if(!isAvailable(seatNumber)) throw new IllegalStateException("Seat not available: " + seatNumber);
        occupancy.put(seatNumber, true);
    }

    public void release(String seatNumber) {
        if (!occupancy.containsKey(seatNumber)) {
            throw new IllegalStateException("Unknown seat: " + seatNumber);
        }
        if (!Boolean.TRUE.equals(occupancy.get(seatNumber))) {
            throw new IllegalStateException("Seat is not occupied: " + seatNumber);
        }
        occupancy.put(seatNumber, false);
    }

    public List<String> freeSeats(){
        List<String> r = new ArrayList<>();
        occupancy.forEach((n, occ) -> { if(!occ) r.add(n); });
        return r;
    }
}

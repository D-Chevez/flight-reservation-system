package org.kodigo.flights.model;

import java.util.*;

public final class SeatMap {
    private final Map<String, Boolean> occupancy = new HashMap<>(); // seatNumber -> occupied

    public SeatMap(Collection<Seat> seats) {
        for (Seat s : seats) occupancy.put(s.number(), false);
    }
    public boolean isAvailable(String seatNumber){ return occupancy.containsKey(seatNumber) && !occupancy.get(seatNumber); }
    public void occupy(String seatNumber){
        if(!isAvailable(seatNumber)) throw new IllegalStateException("Seat not available: " + seatNumber);
        occupancy.put(seatNumber, true);
    }
    public Set<String> freeSeats(){
        Set<String> r = new LinkedHashSet<>();
        occupancy.forEach((n,occ) -> { if(!occ) r.add(n); });
        return r;
    }
}

package org.kodigo.passengers.repository;

import org.kodigo.passengers.model.Passenger;

import java.util.*;

public class InMemoryPassengerRepository implements passengerRepository{

    private final Map<UUID, Passenger> storage = new HashMap<>();

    @Override
    public Passenger save(Passenger passenger){
        storage.put(passenger.id(),passenger);
        return passenger;
    }

    @Override
    public Optional<Passenger> findById(UUID id){
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Passenger> findByPassport(String passport){
        return storage.values().stream().filter(p -> p.passport().equalsIgnoreCase(passport)).findFirst();
    }

    @Override
    public List<Passenger> findAll(){
        return List.copyOf(storage.values());
    }

    @Override
    public void delete(UUID id){
        storage.remove(id);
    }


}

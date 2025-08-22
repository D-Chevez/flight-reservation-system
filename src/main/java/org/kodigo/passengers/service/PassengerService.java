package org.kodigo.passengers.service;

import org.kodigo.passengers.model.Passenger;
import org.kodigo.passengers.repository.passengerRepository;

import java.util.List;
import java.util.Optional;

public class PassengerService {

    private final passengerRepository repository;

    public PassengerService(passengerRepository repository) {
        this.repository = repository;
    }

    public Passenger createPassenger(String fullName, String passport){
        if (repository.findByPassport(passport).isPresent()){
            throw new IllegalArgumentException("El pasaporte ya existe!");
        }
        Passenger passenger = new Passenger(fullName, passport);
        return repository.save(passenger);
    }

    public List<Passenger> listPassengers(){
        return repository.findAll();
    }
    public void updateContacts(String passport, String email, String phone) {
        Passenger passenger = repository.findByPassport(passport)
                .orElseThrow(() -> new IllegalArgumentException("Pasajero no encontrado"));
        passenger.updateContacts(email, phone);
        repository.save(passenger);
    }

    public void suspendPassenger(String passport) {
        Passenger passenger = repository.findByPassport(passport)
                .orElseThrow(() -> new IllegalArgumentException("Pasajero no encontrado"));
        passenger.suspend();
        repository.save(passenger);
    }

    public void reactivatePassenger(String passport) {
        Passenger passenger = repository.findByPassport(passport)
                .orElseThrow(() -> new IllegalArgumentException("Pasajero no encontrado"));
        passenger.reactivate();
        repository.save(passenger);
    }

    public Passenger findByPassport(String passport) {
        var opt = repository.findByPassport(passport);

        if(opt.isEmpty()) throw new IllegalArgumentException("Passenger '" +passport+ "' not found.");

        return opt.get();
    }


    public void deletePassenger(String passport) {
        Passenger passenger = findByPassport(passport);

        repository.delete(passenger.id());
    }




}

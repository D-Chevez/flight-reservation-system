package org.kodigo;

import org.kodigo.bookings.service.booking.BookingServiceFactory;
import org.kodigo.bookings.service.booking.IBookingService;
import org.kodigo.checkin.service.CheckInServiceFactory;
import org.kodigo.checkin.service.ICheckInService;
import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.nav.Router;

import org.kodigo.flights.service.airport.AirportServiceFactory;
import org.kodigo.flights.service.airport.IAirportService;
import org.kodigo.flights.service.flight.FlightServiceFactory;
import org.kodigo.flights.service.flight.IFlightService;
import org.kodigo.passengers.repository.InMemoryPassengerRepository;
import org.kodigo.passengers.repository.passengerRepository;
import org.kodigo.passengers.service.PassengerService;


public class Main {
    public static void main(String[] args) {
        IAirportService airports = AirportServiceFactory.build();
        IFlightService flights = FlightServiceFactory.build(airports);

        passengerRepository passengerRepo = new InMemoryPassengerRepository();
        PassengerService passengers = new PassengerService(passengerRepo);
        IBookingService bookings = BookingServiceFactory.build(flights, passengers);
        ICheckInService checkin = CheckInServiceFactory.build(bookings);

        AppContext ctx = new AppContext(airports, flights, bookings, passengers, checkin);
        new Router(ctx).run();
    }
}

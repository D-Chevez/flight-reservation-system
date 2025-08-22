package org.kodigo.cli.app;

import org.kodigo.flights.service.airport.IAirportService;
import org.kodigo.flights.service.flight.IFlightService;
import org.kodigo.bookings.service.booking.IBookingService;
import org.kodigo.checkin.service.ICheckInService;
import org.kodigo.passengers.service.PassengerService;

public class AppContext {
    public final IAirportService airports;
    public final IFlightService flights;
    public final IBookingService bookings;
    public final PassengerService passengers;
    public final ICheckInService checkin;

    public AppContext(IAirportService airports,
                      IFlightService flights,
                      IBookingService bookings,
                      PassengerService passengers,
                      ICheckInService checkin) {
        this.airports = airports;
        this.flights = flights;
        this.bookings = bookings;
        this.passengers = passengers;
        this.checkin = checkin;
    }
}

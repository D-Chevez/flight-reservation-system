package org.kodigo.cli.nav;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.screens.MainMenuFactory;
import org.kodigo.cli.screens.airports.AirportsMenuFactory;
import org.kodigo.cli.screens.bookings.BookingsMenuFactory;
import org.kodigo.cli.screens.flights.FlightsMenuFactory;
import org.kodigo.cli.screens.passengers.PassengersMenuFactory;

public class Router {
    private final AppContext ctx;
    private final NavigationStack nav = new NavigationStack();

    // contextual state
    public String selectedAirportId;
    public String selectedFlightId;
    public String selectedPassengerId;
    public String selectedBookingId;

    public Router(AppContext ctx) {
        this.ctx = ctx;
        nav.push(Screen.MAIN);
    }

    public void run() {
        while (nav.current() != Screen.EXIT) {
            Menu menu = switch (nav.current()) {
                case AIRPORTS -> new AirportsMenuFactory(this, ctx).build();
                case FLIGHTS -> new FlightsMenuFactory(this, ctx).build();
                case BOOKINGS -> new BookingsMenuFactory(this, ctx).build();
                case PASSENGERS -> new PassengersMenuFactory(this, ctx).build();
                default -> new MainMenuFactory(this, ctx).build();
            };
            menu.showAndRun();
        }
    }

    public void go(Screen s){ nav.push(s); }
    public void back(){ nav.pop(); }
    public void exit(){ while (nav.current()!=Screen.EXIT) nav.push(Screen.EXIT); }
}

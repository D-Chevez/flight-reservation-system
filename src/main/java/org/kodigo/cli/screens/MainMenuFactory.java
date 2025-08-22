package org.kodigo.cli.screens;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.nav.Router;
import org.kodigo.cli.nav.Screen;

public class MainMenuFactory {
    private final Router router;
    private final AppContext ctx;
    public MainMenuFactory(Router router, AppContext ctx) {
        this.router = router; this.ctx = ctx;
    }

    public Menu build() {
        return new Menu("Main Menu")
                .add(new MenuItem("Airports", this::goAirports))
                .add(new MenuItem("Passengers", this::goPassengers))
                .add(new MenuItem("Flights", this::goFlight))
                .add(new MenuItem("Bookings", this::goBookings))
                .add(new MenuItem("Exit", router::exit));
    }

    private void goAirports(){
        Console.clear();
        router.go(Screen.AIRPORTS);
    }

    private void goFlight(){
        Console.clear();
        router.go(Screen.FLIGHTS);
    }

    private void goBookings(){
        Console.clear();
        router.go(Screen.BOOKINGS);
    }

    private void goPassengers(){
        Console.clear();
        router.go(Screen.PASSENGERS);
    }
}

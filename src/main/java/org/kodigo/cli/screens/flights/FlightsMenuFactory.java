package org.kodigo.cli.screens.flights;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;
import org.kodigo.flights.model.Flight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightsMenuFactory {
    private final Router router;
    private final AppContext ctx;

    public FlightsMenuFactory(Router router, AppContext ctx) {
        this.router = router; this.ctx = ctx;
    }

    public Menu build() {
        return new Menu("Flights")
                .add(new MenuItem("List flights", SafeCommand.of(this.router, this::listFlights)))
                .add(new MenuItem("View flight", SafeCommand.of(this.router, this::findFlight)))
                .add(new MenuItem("Create flight", SafeCommand.of(this.router, this::createFlight)))
                .add(new MenuItem("Back", router::back))
                .add(new MenuItem("Exit", router::exit));
    }

    public void createFlight(){
        Console.clear();
        String code = Input.readString("Enter flight code: ");

        Console.clear();
        String originAirportCode = Input.readString("Enter origin airport code: ");

        Console.clear();
        String destinationAirportCode = Input.readString("Enter destination airport code: ");

        Console.clear();
        List<String> seats = new ArrayList<>();
        while (true) {
            String seat = Input.readStringAllowEmpty("Enter a seat (Leave blank to finish): ");
            if (seat.trim().isEmpty()) break;
            seats.add(seat.trim());
        }


        Console.clear();
        LocalDate date = Input.readDate("Enter flight date (YYYY-MM-DD): ");

        Console.clear();
        BigDecimal baseFare = Input.readMoney("Enter base fare: ");

        var created = ctx.flights.create(code, originAirportCode, destinationAirportCode, seats, date, baseFare);

        Console.clear();
        Console.println("Flight created successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    private void listFlights() {
        Console.clear();

        List<Flight> all = ctx.flights.getAll();
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Code","From","To","Date"});
        for (Flight f : all) {
            rows.add(new String[]{ f.code(), f.origin().code(), f.destination().code(), f.date().toString() });
        }
        TablePrinter.simple("Flights", rows);

        new FootMenu().add(new MenuItem("Back", () -> {})).add(new MenuItem("Back to main", router::back)).showAndRun();
    }

    private void findFlight() {
        Console.clear();
        String code = Input.readString("Flight code: ");

        Console.clear();
        var flight = ctx.flights.getByCode(code);

        router.selectedFlightId = code;

        Console.clear();
        new FlightDetailMenuFactory(router, ctx, flight).build().showAndRun();
    }
}

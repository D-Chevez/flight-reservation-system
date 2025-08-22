package org.kodigo.cli.screens.flights;

import org.kodigo.bookings.model.Booking;
import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;
import org.kodigo.cli.nav.Screen;
import org.kodigo.flights.model.Flight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightDetailMenuFactory {
    private final Router router;
    private final AppContext ctx;
    private final Flight flight;

    public FlightDetailMenuFactory(Router router, AppContext ctx, Flight flight) {
        this.router = router; this.ctx = ctx; this.flight = flight;
    }

    public FootMenu build() {
        Console.println("== " +flight.code()+ " ==");
        Console.println("- From:" + flight.origin().toString().trim());
        Console.println("- To:" + flight.destination().toString().trim());
        Console.println("- Date:" + flight.date().toString().trim());
        Console.println("- Base Fare:" + flight.baseFare().toString().trim());

        boolean canModify = LocalDate.now().isBefore(flight.date());

        return new FootMenu()
                .add(new MenuItem("Cancel", SafeCommand.of(this.router, this::cancel), () -> canModify))
                .add(new MenuItem("Modify", SafeCommand.of(this.router, this::modify), () -> canModify))
                .add(new MenuItem("List bookings", SafeCommand.of(this.router, this::listBookings)))
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Main menu", router::back))
                .add(new MenuItem("Exit", router::exit));
    }

    private void cancel() {
        boolean confirm = Input.confirm("Are you sure you want to cancel this flight? (yes/no)");

        if (!confirm) {
            Console.println("Flight cancellation aborted.");
            return;
        }
        ctx.flights.delete(flight.code());

        Console.clear();
        Console.println("Flight deleted successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    private void modify() {
        Console.clear();
        Console.println("Current origin airport code: " + flight.origin().code());
        String newAirportCode = Input.readString("Enter new origin airport code: ");

        Console.clear();
        Console.println("Current destination airport code: " + flight.destination().code());
        String newDestinationCode = Input.readString("Enter new destination airport code: ");

        Console.clear();
        if (Input.confirm("Are you sure you want to modify this flight? (yes/no))")) {
            Console.println("Flight modification aborted.");
            return;
        }

        ctx.flights.update(flight.code(), newAirportCode, newDestinationCode);

        Console.clear();
        Console.println("Airport updated successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    private void listBookings() {
        Console.clear();

        List<Booking> bookings = ctx.bookings.getByFlightCode(flight.code());

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Code","Passenger","Seat","Total","State"});
        for (Booking b : bookings) {
            rows.add(new String[]{ b.code(), b.passenger().toString(), b.seatNumber(), b.total().toString(), b.state().name() });
        }
        TablePrinter.simple("Bookings", rows);

        new FootMenu()
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Back to main", router::back))
                .add(new MenuItem("Exit", router::exit))
                .showAndRun();
    }
}

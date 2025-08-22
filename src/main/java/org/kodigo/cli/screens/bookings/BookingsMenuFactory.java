package org.kodigo.cli.screens.bookings;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.bookings.model.Booking;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;

import java.util.ArrayList;
import java.util.List;

public class BookingsMenuFactory {
    private final AppContext ctx;
    private final Router router;

    public BookingsMenuFactory(Router router, AppContext ctx){
        this.ctx = ctx;
        this.router = router;
    }

    public Menu build(){
        return new Menu("Bookings")
                .add(new MenuItem("List bookings", SafeCommand.of(this.router, this::listAll)))
                .add(new MenuItem("View booking", SafeCommand.of(this.router, this::find)))
                .add(new MenuItem("Create booking", SafeCommand.of(this.router, this::create)))
                .add(new MenuItem("Back", router::back))
                .add(new MenuItem("Exit", router::exit));
    }

    private void create(){
        Console.clear();
        String code = Input.readString("Booking code: ");
        Console.clear();
        String flightCode = Input.readString("Flight code: ");
        Console.clear();
        String passengerPassport = Input.readString("Passenger passport: ");

        var optFlight = ctx.flights.getByCode(flightCode);
        List<String> seats = ctx.flights.availableSeats(flightCode);
        Console.clear();
        Console.println("Available seats: " + String.join(", ", seats));
        String seat = Input.readString("Seat: ");

        ctx.bookings.create(code, flightCode, passengerPassport, seat);
        Console.clear();
        Console.println("Passenger created successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    private void listAll(){
        var list = ctx.bookings.list();
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Code","Flight","Passenger","Status"});
        for(Booking b : list){
            rows.add(new String[]{ b.code(), b.flight().toString(), b.passenger().toString(), b.state().name() });
        }
        TablePrinter.simple("Bookings", rows);

        new FootMenu()
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Go to main", router::back))
                .add(new MenuItem("Exit", router::exit))
                .showAndRun();
    }

    private void find(){
        String code = Input.readString("Booking code: ");
        var booking = ctx.bookings.getByCode(code);

       router.selectedBookingId = booking.code();

       Console.clear();
        new BookingDetailMenuFactory(router, ctx, booking)
                .build()
                .showAndRun();
    }
}

package org.kodigo.cli.screens.bookings;

import org.kodigo.bookings.model.Booking;
import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;

import java.util.List;

public class BookingDetailMenuFactory {
    private final Router router;
    private final AppContext ctx;
    private final Booking booking;

    public BookingDetailMenuFactory(
            Router router,
            AppContext ctx,
            Booking booking
    ){
        this.router = router;
        this.ctx = ctx;
        this.booking = booking;
    }

    public Menu build() {
        Console.println(booking.toString());

        boolean isConfirmed = booking.state().compareTo(Booking.BookingState.CONFIRMED) == 0;
        boolean isCheckedIn = booking.state().compareTo(Booking.BookingState.CHECKED_IN) == 0;

        return new Menu("Booking " + booking.code())
                .add(new MenuItem("Modify booking", SafeCommand.of(this.router, this::modify), () -> isConfirmed))
                .add(new MenuItem("Cancel booking", SafeCommand.of(this.router, this::cancel), () -> isConfirmed))
                .add(new MenuItem("Check-in", SafeCommand.of(this.router, this::checkIn), () -> isConfirmed))
                .add(new MenuItem("Show boarding pass", SafeCommand.of(this.router, this::showPass), () -> isCheckedIn))
                .add(new MenuItem("Back to Bookings", router::back));
    }

    private void modify() {
        Console.clear();
        List<String> seats = ctx.flights.availableSeats(booking.flight().code());
        Console.println("Available seats: " + String.join(", ", seats));
        String newSeat = Input.readString("New seat: ");

        if (!Input.confirm("Are you sure you want to modify this booking? (yes/no)")) {
            this.continueHelper("Booking modification aborted.");
        }

        ctx.bookings.changeSeat(booking.code(), newSeat);
        this.continueHelper("Booking modified successfully.");
    }

    private void cancel() {
        Console.clear();
        if(!Input.confirm("Are you sure you cancel this?")){
            this.continueHelper("Booking cancellation aborted.");
        }

        ctx.bookings.cancel(booking.code());
        this.continueHelper("Booking canceled successfully.");
    }

    private void checkIn() {
        Console.clear();
        if(!Input.confirm("Are you sure you want check this?")){
            this.continueHelper("Booking checking aborted.");
        }

        ctx.bookings.checkIn(booking.code());
        this.continueHelper("Booking checking successfully.");
    }

    private void showPass() {
        Console.clear();
        var checkin = ctx.checkin.checkIn(booking.code(), booking.passenger().passport(), booking.seatNumber());
        Console.println("\n--- Boarding Pass ---");
        Console.println(checkin.toString());
        Console.println("--- End of Boarding Pass ---\n");
    }

    private void continueHelper(String message){
        Console.clear();
        if (!message.isEmpty()){
            Console.println(message);
        }
        if(Input.confirm("Would you like to go out?")) router.exit();
    }
}

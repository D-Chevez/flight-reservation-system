package org.kodigo.cli.screens.passengers;

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
import org.kodigo.flights.model.Flight;
import org.kodigo.passengers.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengerDetailMenuFactory {
    private final Router router;
    private final AppContext ctx;
    private final Passenger passenger;

    public PassengerDetailMenuFactory(
            Router router,
            AppContext ctx,
            Passenger passenger
    ){
        this.router = router;
        this.ctx = ctx;
        this.passenger = passenger;
    }

    public FootMenu build() {
        Console.println("== Passenger: " + passenger.passport() + " ==");
        Console.println("- Name: " + passenger.fullName());
        Console.println("- Passport: " + passenger.passport());
        Console.println("- Phone: " + passenger.phone());
        Console.println("- Email: " + passenger.email());
        Console.println("- Status: " + passenger.status().name());

        boolean canSuspend = passenger.status().compareTo(Passenger.Status.ACTIVE) == 0;
        boolean canActivate = passenger.status().compareTo(Passenger.Status.SUSPENDED) == 0;

        return new FootMenu()
                .add(new MenuItem("Modify", SafeCommand.of(this.router, this::modify)))
                .add(new MenuItem("Suspend", SafeCommand.of(this.router, this::suspend), () -> canSuspend))
                .add(new MenuItem("Reactivate", SafeCommand.of(this.router, this::activate), () -> canActivate))
                .add(new MenuItem("List bookings", SafeCommand.of(this.router, this::listBookings)))
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Go to main", router::back))
                .add(new MenuItem("Exit", router::exit));
    }


    private void modify(){
        Console.clear();
        Console.println("Current email: " + passenger.email());
        String newEmail = Input.readString("New email: ");

        Console.clear();
        Console.println("Current phone: " + passenger.phone());
        String newPhone = Input.readString("New phone: ");

        Console.clear();
        if (!Input.confirm("Are you sure you want update this?")){
            this.continueHelper("Update passenger aborted.");
        }

        ctx.passengers.updateContacts(passenger.passport(), newEmail, newPhone);
        this.continueHelper("Passenger updated successfully.");
    }

    private void suspend(){
        Console.clear();
        if(!Input.confirm("Are you sure you want suspend this?")){
            this.continueHelper("Suspend passenger aborted.");
        }

        ctx.passengers.suspendPassenger(passenger.passport());
        this.continueHelper("Passenger suspended successfully.");
    }

    private void activate(){
        Console.clear();
        if(!Input.confirm("Are you sure you want activate this?")){
            this.continueHelper("Active passenger aborted.");
        }

        ctx.passengers.suspendPassenger(passenger.passport());
        this.continueHelper("Passenger activated successfully.");
    }

    private void listBookings(){
        Console.clear();

        var list = ctx.bookings.getByPassengerPassport(passenger.passport());
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Code","Flight","Status"});
        for(Booking b : list){
            rows.add(new String[]{ b.code(), b.flight().toString(), b.state().name() });
        }
        TablePrinter.simple("Bookings - " + passenger.passport(), rows);

        new FootMenu()
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Go to main", router::back))
                .add(new MenuItem("Exit", router::exit))
                .showAndRun();
    }

    private void continueHelper(String messaje){
        Console.clear();
        if (!messaje.isEmpty()){
            Console.println(messaje);
        }
        if(Input.confirm("Would you like to go out?")) router.exit();
    }
}

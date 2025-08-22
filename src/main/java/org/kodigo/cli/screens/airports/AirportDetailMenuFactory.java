package org.kodigo.cli.screens.airports;

import org.kodigo.bookings.model.Booking;
import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;
import org.kodigo.flights.model.Airport;
import org.kodigo.flights.model.Flight;
import org.kodigo.passengers.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class AirportDetailMenuFactory {
    private final Router router;
    private final AppContext ctx;
    private final Airport airport;

    public AirportDetailMenuFactory(Router router, AppContext ctx, Airport airport) {
        this.router = router; this.ctx = ctx; this.airport = airport;
    }

    public FootMenu build() {
        Console.println("== Airport: " + airport.code() + " ==");
        Console.println("- Name: " + airport.name());
        Console.println("- City: " + airport.city());
        Console.println("- Country: " + airport.country());

        return new FootMenu()
                .add(new MenuItem("Modify airport",
                        SafeCommand.of(this.router, this::modify)))
                .add(new MenuItem("Delete airport",
                        SafeCommand.of(this.router, this::delete)))
                .add(new MenuItem("View flights",
                        SafeCommand.of(this.router, this::listFlights)))
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Go to Main", router::back));
    }

    private void modify(){
        Console.clear();
        Console.println("Current name airport: " + airport.name());
        String newName = Input.readStringAllowEmpty("Enter new name airport: ");
        Console.clear();
        Console.println("Current city airport: " + airport.city());
        String newCity = Input.readStringAllowEmpty("Enter new city airport: ");
        Console.clear();
        Console.println("Current country airport: " + airport.country());
        String newCountry = Input.readStringAllowEmpty("Enter new country airport: ");

        Console.clear();
        if(!Input.confirm("Are you sure you want update this? (yes/no))"))
        {
            this.continueHelper("Airport modification aborted.");
        }

        ctx.airports.update(airport.code(),
                newName.isEmpty() ? airport.name() : newName,
                newCity.isEmpty() ? airport.city() : newCity,
                newCountry.isEmpty() ? airport.country() : newCountry);

        this.continueHelper("Airport updated successfully.");
    }

    private void delete(){
        Console.clear();
        if(!Input.confirm("Are you sure you want to delete this?"))
        {
            this.continueHelper("Airport modification aborted.");
        }
        ctx.airports.delete(airport.code());

        this.continueHelper("Airport deleted successfully.");
    }

    private void listFlights(){
        Console.clear();

        var list = ctx.flights.getByAirportCode(airport.code());
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Code","Origin","Destination","Date"});
        for(Flight f : list){
            rows.add(new String[]{ f.code(), f.origin().toString(), f.destination().toString(), f.date().toString() });
        }
        TablePrinter.simple("Bookings - " + airport.code(), rows);

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

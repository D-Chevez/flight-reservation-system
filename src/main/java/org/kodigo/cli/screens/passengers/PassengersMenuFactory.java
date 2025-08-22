package org.kodigo.cli.screens.passengers;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;
import org.kodigo.passengers.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengersMenuFactory {
    private final Router router;
    private final AppContext ctx;

    public PassengersMenuFactory(Router router, AppContext ctx){
        this.ctx = ctx;
        this.router = router;
    }

    public Menu build(){
        return new Menu("Passengers")
                .add(new MenuItem("List passengers", SafeCommand.of(this.router, this::listAll)))
                .add(new MenuItem("View passenger", SafeCommand.of(this.router, this::find)))
                .add(new MenuItem("Register passenger", SafeCommand.of(this.router, this::create)))
                .add(new MenuItem("Back", router::back));
    }

    private void create(){
        Console.clear();
        String fullname = Input.readString("Full name: ");

        Console.clear();
        String passport = Input.readString("Passport: ");

        var p = ctx.passengers.createPassenger(fullname, passport);

        Console.clear();
        Console.println("Passenger created successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    private void listAll(){
        Console.clear();

        var list = ctx.passengers.listPassengers();
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Passport","Name","Status"});
        for(Passenger p : list){
            rows.add(new String[]{ p.passport(), p.fullName(), p.status().name()});
        }
        TablePrinter.simple("Passengers", rows);

        new FootMenu()
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Go to main", router::back)).showAndRun();
    }

    private void find(){
        Console.clear();

        String passport = Input.readString("Passenger passport: ");

        var passenger = ctx.passengers.findByPassport(passport);

        router.selectedPassengerId = passenger.id().toString();

        Console.clear();
        new PassengerDetailMenuFactory(router, ctx, passenger)
                .build()
                .showAndRun();
    }
}

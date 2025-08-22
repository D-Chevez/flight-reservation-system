package org.kodigo.cli.screens.airports;

import org.kodigo.cli.app.AppContext;
import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.core.TablePrinter;
import org.kodigo.cli.menu.FootMenu;
import org.kodigo.cli.menu.Menu;
import org.kodigo.cli.menu.MenuItem;
import org.kodigo.cli.menu.SafeCommand;
import org.kodigo.cli.nav.Router;
import org.kodigo.flights.model.Airport;

import java.util.ArrayList;
import java.util.List;

public class AirportsMenuFactory {
    private final Router router;
    private final AppContext ctx;

    public AirportsMenuFactory(Router router, AppContext ctx) {
        this.router = router; this.ctx = ctx;
    }

    public Menu build() {
        return new Menu("Flights")
                .add(new MenuItem("List airport", SafeCommand.of(this.router, this::list)))
                .add(new MenuItem("View airport", SafeCommand.of(this.router, this::find)))
                .add(new MenuItem("Create airport", SafeCommand.of(this.router, this::create)))
                .add(new MenuItem("Back", router::back));
    }

    void create() {
        Console.clear();
        String code = Input.readString("Enter airport code: ");

        Console.clear();
        String name = Input.readString("Enter airport name: ");

        Console.clear();
        String city = Input.readString("Enter airport city: ");

        Console.clear();
        String country = Input.readString("Enter airport country: ");

        ctx.airports.create(code, name, city, country);

        Console.clear();
        Console.println("Airport created successfully.");
        if(Input.confirm("Would you like to go out?")) router.exit();
    }

    void list() {
        Console.clear();

        List<Airport> airports = ctx.airports.list();
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{"Code","Name","City","Country"});
        for (Airport a : airports) {
            rows.add(new String[]{ a.code(), a.name(), a.city(), a.country() });
        }
        TablePrinter.simple("Airports", rows);

        new FootMenu()
                .add(new MenuItem("Back", () -> {}))
                .add(new MenuItem("Back to main", router::back))
                .add(new MenuItem("Exit", router::exit))
                .showAndRun();
    }

    void find() {
        Console.clear();
        String code = Input.readString("Enter airport code: ");

        Console.clear();
        var airport = ctx.airports.getByCode(code);
        router.selectedAirportId = code;

        Console.clear();
        new AirportDetailMenuFactory(router, ctx, airport).build().showAndRun();
    }

}

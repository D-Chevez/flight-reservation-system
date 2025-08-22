package org.kodigo.cli.menu;

import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;
import org.kodigo.cli.nav.Router;

public class SafeCommand implements Command {
    private final Router router;
    private final Command delegate;

    public SafeCommand(Router router, Command delegate){
        this.router = router;
        this.delegate = delegate;

    }

    @Override
    public void execute() {
        try {
            delegate.execute();
        } catch (Exception e) {
            Console.clear();
            Console.println("Error: " + e.getMessage());
            if(!Input.confirm("Do you want to tray again?")) router.exit();
        }
    }

    public static Command of(Router router, Command c){
        return new SafeCommand(router, c);
    }
}
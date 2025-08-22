package org.kodigo.cli.menu;

import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String title;
    private final List<MenuItem> items = new ArrayList<>();

    public Menu(String title) { this.title = title; }

    public Menu add(MenuItem item) { items.add(item); return this; }

    public void showAndRun() {
        Console.clear();
        var visible = items.stream().filter(MenuItem::isVisible).toList();
        while (true) {
            Console.println("\n== " + title + " ==");
            for (int i = 0; i < visible.size(); i++) {
                Console.println((i + 1) + ") " + visible.get(i).label);
            }
            Console.print("Choose: ");
            int opt = Input.readIntInRange(1, visible.size());
            Console.println("");
            visible.get(opt - 1).action.execute();
            break;
        }
    }
}

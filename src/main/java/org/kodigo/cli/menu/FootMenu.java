package org.kodigo.cli.menu;

import org.kodigo.cli.core.Console;
import org.kodigo.cli.core.Input;

import java.util.ArrayList;
import java.util.List;

public class FootMenu {
    private final List<MenuItem> items = new ArrayList<>();

    public FootMenu add(MenuItem item) { items.add(item); return this; }

    public void showAndRun() {
        var visible = items.stream().filter(MenuItem::isVisible).toList();
        while (true) {
            String menu = "\n";
            for (int i = 0; i < visible.size(); i++) {
                menu += "[" + (i + 1) + "] " + visible.get(i).label;
                if (i < visible.size() - 1) {
                    menu += " | ";
                }
            }
            Console.println(menu);
            Console.print("Choose: ");
            int opt = Input.readIntInRange(1, visible.size());
            Console.println("");
            visible.get(opt - 1).action.execute();
            break;
        }
    }
}

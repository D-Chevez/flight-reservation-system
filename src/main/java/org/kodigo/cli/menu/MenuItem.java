package org.kodigo.cli.menu;

import java.util.function.BooleanSupplier;

public class MenuItem {
    public final String label;
    public final Command action;
    private final BooleanSupplier visible;

    public MenuItem(String label, Command action) {
        this(label, action, () -> true);
    }
    public MenuItem(String label, Command action, BooleanSupplier visible) {
        this.label = label;
        this.action = action;
        this.visible = visible;
    }
    public boolean isVisible() { return visible.getAsBoolean(); }
}


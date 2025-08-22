package org.kodigo.cli.core;

import java.util.List;

public final class TablePrinter {
    public static void simple(String title, List<String[]> rows) {
        Console.println("\n== " + title + " ==");
        if (rows == null || rows.isEmpty()) {
            Console.println("(no items)\n");
            return;
        }
        int cols = rows.get(0).length;
        int[] w = new int[cols];
        for (String[] r : rows) for (int i=0;i<cols;i++) w[i] = Math.max(w[i], r[i].length());
        for (String[] r : rows) {
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<cols;i++) sb.append(String.format("%-" + (w[i]+2) + "s", r[i]));
            Console.println(sb.toString());
        }
        Console.println("");
    }
}

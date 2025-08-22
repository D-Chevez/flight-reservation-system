package org.kodigo.cli.core;

import java.util.Scanner;

public final class Console {
    private static final Scanner SC = new Scanner(System.in);

    public static void println(String s) { System.out.println(s); }

    public static void print(String s)   { System.out.print(s); }

    public static String readLine()      { return SC.nextLine().trim(); }

    public static void clear(){
        try {
            for (int i = 0; i < 10; i++) {
                println("");
            }
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
}

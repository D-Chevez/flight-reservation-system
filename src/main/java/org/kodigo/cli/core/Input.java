package org.kodigo.cli.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public final class Input {

    public static int readIntInRange(int min, int max) {
        while (true) {
            try {
                String s = Console.readLine();
                int n = Integer.parseInt(s);
                if (n < min || n > max) throw new NumberFormatException();
                return n;
            } catch (NumberFormatException ex) {
                Console.print("Invalid option. Try again: ");
            }
        }
    }

    public static BigDecimal readMoney(String prompt) {
        Console.print(prompt);
        while (true) {
            try {
                String s = Console.readLine();
                BigDecimal amount = new BigDecimal(s);
                if (amount.signum() < 0) throw new NumberFormatException();
                return amount;
            } catch (NumberFormatException ex) {
                Console.print("Invalid amount. Try again: ");
            }
        }
    }

   public static LocalDate readDate(String prompt) {
        Console.print(prompt);
        while (true) {
            try {
                String s = Console.readLine();
                LocalDate date = LocalDate.parse(s);
                return date;
            } catch (Exception ex) {
                Console.print("Invalid date format. Use YYYY-MM-DD. Try again: ");
            }
        }
    }

    public static String readString(String prompt) {
        Console.print(prompt);
        while (true) {
            String s = Console.readLine();
            if (!s.isEmpty()) return s;
            Console.print("Required. Try again: ");
        }
    }

    public static String readStringAllowEmpty(String prompt){
        Console.print(prompt);
        return Console.readLine();
    }

    public static boolean confirm(String q) {
        Console.print(q + " (y/n): ");
        while (true) {
            String s = Console.readLine().toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            Console.print("Please answer y/n: ");
        }
    }
}

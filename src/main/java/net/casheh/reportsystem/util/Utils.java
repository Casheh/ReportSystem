package net.casheh.reportsystem.util;

public class Utils {

    public static String convert(String[] args) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < args.length; i++) { // Start at index 1 to allow for arg[0] to be a player.
            sb.append(args[i]).append(" ");
        }
        return sb.toString().trim();
    }
}

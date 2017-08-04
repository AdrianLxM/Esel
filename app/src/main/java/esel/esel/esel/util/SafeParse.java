package esel.esel.esel.util;

/**
 * Created by mike on 23.06.2016.
 */
public class SafeParse {
    public static Double stringToDouble(String input) {
        Double result = 0d;
        input = input.replace(",", ".");
        if (input.equals(""))
            return 0d;
        try {
            result = Double.parseDouble(input);
        } catch (Exception e) {
        }
        return result;
    }

    public static Integer stringToInt(String input) {
        Integer result = 0;
        input = input.replace(",", ".");
        if (input.equals(""))
            return 0;
        try {
            result = Integer.parseInt(input);
        } catch (Exception e) {
        }
        return result;
    }

   public static Long stringToLong(String input) {
        Long result = 0L;
        input = input.replace(",", ".");
       if (input.equals(""))
           return 0L;
        try {
            result = Long.parseLong(input);
        } catch (Exception e) {
        }
        return result;
    }
}

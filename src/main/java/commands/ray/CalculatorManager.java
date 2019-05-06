package commands.ray;

import commands.ray.calculator.Calculator;

public class CalculatorManager {

    private static Calculator calculator = new Calculator();
    private static String syntax = "&c [expression]";
    private static String helpMessage = "Calculate a value: ``[" + syntax + "]``";

    public static String getResponse(String str) {
        try {
            str = str.toLowerCase();

            if (str.equals("&c")) {
                return helpMessage;
            }

            if (str.startsWith("&c")) {
                if (str.length() >= 4) {
                    double result;
                    try {
                        result = calculator.calculate(str.substring(2));
                    } catch (Exception e) {
                        return "Calculator error: " + e.getMessage();
                    }
                    if (result % 1 == 0) {
                        return Integer.toString((int) result);
                    }
                    return Double.toString(result);
                } else if (str.length() == 3 && isNumeric(str.charAt(2))) {
                    return str.substring(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return null;
    }

    private static boolean isNumeric(char character) {
        try {
            Double.parseDouble(String.valueOf(character));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
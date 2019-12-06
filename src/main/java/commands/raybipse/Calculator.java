package commands.raybipse;

import java.util.concurrent.ThreadLocalRandom;

class Calculator extends Master {

    private static int minRandom = -2;
    private static int maxRandom = 2;

    private static String[] operations = new String[] { "+", "-", "/", "*" };
    private static String helpMessage = "Syntax: (number 1) (operation) (number 2) \nExample: 24 + 24 \nOperations: "
            + String.join(", ", operations);

    protected static String getResponse(String str) {
        if (str.length() < 2)
            return null;
        if (!(str.toLowerCase().startsWith("&c")))
            return null;

        str = removeWhiteSpace(str);

        if (str.equals("&c") || str.equals("&C"))
            return helpMessage;
        str = str.substring(2);

        try {
            String[] parts = splitParts(str);
            if (parts == null) {
                return "Error: invalid syntax";
            }

            double num1 = Double.parseDouble(parts[0]), num2 = Double.parseDouble(parts[1]);
            String operation = parts[2];

            return "The result is: " + getMathResult(num1, num2, operation);
        } catch (IllegalArgumentException e) {
            if (e instanceof NumberFormatException)
                return "Error: invalid syntax";
            return "Error: " + e.getLocalizedMessage();
        } catch (Exception e) {
            return "Error: " + e.getLocalizedMessage();
        }
    }

    private static String[] splitParts(String input) {
        String[] parts = new String[] { "Error", "", "" };
        // add
        // -2+2
        // -2 & +2
        // -2 + +2
        // subtract
        // -2-2
        // -2 & -2
        // -2 + -2
        // multiply
        // -2*2
        // -2 * +2
        // divide
        // -2/2
        // -2 / -2

        // If: have * or /, then multiply|divide
        // If not, add both numbers together
        int index = -1;

        // for (int i = 0; i < operations.length; i++) {
        // index = input.indexOf(operations[i]);
        // if (index >= 0) {
        // parts[2] = operations[i];
        // break;
        // }
        // }

        // Find * or /
        if (input.contains("*")) {
            index = input.indexOf("*");
            parts[2] = "*";
        } else if (input.contains("/")) {
            index = input.indexOf("/");
            parts[2] = "/";
        } else {
            // Add - or + behind numbers
            //
        }

        if (index < 0)
            return parts;
        parts[0] = input.substring(0, index);
        parts[1] = input.substring(index + 1);

        return parts;
    }

    private static double getRandomResult(double result) {
        result = result + ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1);

        return result;
    }

    private static double factorial(double num) {
        double result = 1;
        for (double i = 0; num > i; i++) {
            result *= i;
        }
        return result;
    }

    private static double getMathResult(double num1, double num2, String operation) {
        double result = 0;
        switch (operation) {
        case "+":
            result = num1 + num2;
            break;
        case "-":
            result = num1 - num2;
            break;
        case "/":
            if (num2 == 0)
                throw new IllegalArgumentException("Undefined"); // Divide by 0
            result = num1 / num2;
            break;
        case "*":
            result = num1 * num2;
            break;
        case "!":
            result = factorial(num1);
        }
        if (num1 > 100 || num2 > 100)
            result = getRandomResult(result);
        return result;
    }
}
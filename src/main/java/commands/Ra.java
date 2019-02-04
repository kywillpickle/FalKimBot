package commands;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.util.concurrent.ThreadLocalRandom;
import net.dv8tion.jda.core.entities.MessageChannel;

//@Author Ray //
public class Ra {

    public static void messageReceived(String message, MessageChannel channel) {
        channel.sendMessage(Calculator.getResponse(message)).queue();
    }//test

    /************** CALCULATOR **************/
    private static class Calculator {

        private static int minRandom = -2;
        private static int maxRandom = 2;

        private static String[] operations = new String[] { "+", "-", "/", "*" };
        private static String helpMessage = "Syntax: (number 1) (operation) (number 2) \nExample: 24 + 24 \nOperations: "
            + String.join(", ", operations);

        private static String getResponse(String str) {
            if (str.length() < 2)
                return null;
            if (!(str.substring(0, 2).equals("&c")))
                return null;

            str = str.trim();

            if (str.equals("&c"))
                return helpMessage;
            str = str.substring(3);

            try {
                String[] parts = splitParts(str);

                double num1 = Double.parseDouble(parts[0]), num2 = Double.parseDouble(parts[1]);
                String operation = parts[2];

                return "The result is: " + getMathResult(num1, num2, operation);
            } catch (IllegalArgumentException e) {
                return "Error: " + e.getMessage();
            } catch (Exception e) {
                return "Error: " + e;
            }
        }

        private static String[] splitParts(String input) {
            String[] parts = new String[] { "Error", "", "" };

            int index = -1;

            for (int i = 0; i < operations.length; i++) {
                index = input.indexOf(operations[i]);
                if (index >= 0) {
                    parts[2] = operations[i];
                    break;
                }
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
            if (num1 <= 100 || num2 <= 100)
                return result;
            result = getRandomResult(result);
            return result;
        }
    }
}

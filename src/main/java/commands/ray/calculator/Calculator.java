package commands.ray.calculator;

import java.util.ArrayList;

/**
 * <h1>Main controller of calculation methods.</h1>
 * <p>
 * Calculator class converts a string that have a mathematical expression into a
 * double mathematically, and can be instantiated to customize specific
 * operations avaiable, and others.
 *
 * @author Ray Z.
 * @version 1
 * @since 2019-02-9
 */
public class Calculator {
    private Manager manager;

    /**
     * Constructor. With a specified manager instance.
     * 
     * @param manager manages what type of operations can be used, and other
     *                settings
     */
    public Calculator(Manager manager) {
        this.manager = manager;
    }

    /**
     * Constructor. With a default manager instance.
     */
    public Calculator() {
        this(new Manager());
    }

    /**
     * Replaces the current manager with the new manager.
     * 
     * @param manager The future manager
     */
    public void replaceManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Calculates a list of strings that have a mathematical expression combined
     * into a double mathematically.
     * 
     * @param array[] The list of strings to be calculated.
     * @return The calculated number.
     * @throws Exception If the list contains invalid expressions.
     */
    public double calculate(String[] array) throws Exception {
        try {
            Separator.checkArray(array, manager);
            String[] postFixList = Separator.shuntingYard(array).toArray(new String[0]);
            return Separator.calculatePostfix(postFixList);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Calculates a string that have a mathematical expression into a double
     * mathematically.
     * 
     * @param input The string to be calculated.
     * @return The calculated number.
     * @throws Exception If input contains invalid expressions.
     */
    public double calculate(String input) throws Exception {
        try {
            String[] array = assembleArray(input);
            return calculate(array);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Evaluates a string into a list of numbers and operators.
     * 
     * @param input The string to be evaluated.
     * @return The list separated by numbers and operators.
     * @throws Exception If input contains invalid expressions, symbols, or
     *                   operators.
     */
    public String[] assembleArray(String input) throws Exception {
        try {
            input = Separator.combineSigns(Separator.removeWhiteSpace(input));
            ArrayList<String> list = Separator.separateParts(input);
            return list.toArray(new String[0]);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Checks if an array is valid for calculating.
     * 
     * @param array[] The array to be evaluated.
     * @return If array is valid, true if valid.
     */
    public boolean checkValidity(String[] array) {
        try {
            Separator.checkArray(array, manager);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a list is valid for calculating.
     * 
     * @param list[] The list to be evaluated.
     * @return If list is valid, true if valid.
     */
    public boolean checkValidity(ArrayList<String> list) {
        try {
            Separator.checkList(list, manager);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a string is valid for calculating.
     * 
     * @param string The string to be evaluated.
     * @return If string is valid, true if valid.
     */
    public boolean checkValidity(String input) {
        try {
            return checkValidity(assembleArray(input));
        } catch (Exception e) {
            return false;
        }
    }
}
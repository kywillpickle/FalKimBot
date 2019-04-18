package commands.ray.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * <h1>Manager manages settings for Calculator</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray Z.
 * @since 2019-02-9
 */
public class Manager {
    private ArrayList<Operator> allowedOperations = new ArrayList<Operator>(Arrays.asList(Operator.values()));

    /**
     * Constructor. Makes a manager instance with default settings.
     */
    public Manager() {
        removeOperation(Operator.OPEN_PARENTHESIS);
        removeOperation(Operator.CLOSE_PARENTHESIS);
    }

    /**
     * Constructor. With specified allowed operations.
     * 
     * @param operations[] The allowed operations to be used by the calculator.
     */
    public Manager(ArrayList<Operator> operations) {
        allowedOperations = operations;
    }

    /**
     * Returns the allowed operations.
     * 
     * @return The allowed operation.
     */
    public ArrayList<Operator> getAllowedOperations() {
        return allowedOperations;
    }

    /**
     * Sets the operations that is allowed.
     * 
     * @param operations The operations allowed.
     */
    public void setOperations(ArrayList<Operator> operations) {
        allowedOperations = operations;
    }

    /**
     * Adds a new operator to the allowed operations.
     * 
     * @param operator The operator to be added.
     * @throws IllegalArgumentException If operator is OPEN_PARENTHESIS or CLOSE_PARENTHESIS.
     */
    public void addOperation(Operator operator) throws IllegalArgumentException {
        if (operator == Operator.OPEN_PARENTHESIS || operator == Operator.CLOSE_PARENTHESIS) {
            throw new IllegalArgumentException("Tried to add " + operator.toString() + " to allowed operators");
        }
        removeOperation(operator);
        allowedOperations.add(operator);
    }

    /**
     * Removes a operator to the allowed operations.
     * 
     * @param operator The operator to be removed.
     * @return If the operator was ever found, true if yes.
     */
    public boolean removeOperation(Operator operator) throws IllegalArgumentException {
        boolean haveFound = false;
        for (Iterator<Operator> iterator = allowedOperations.iterator(); iterator.hasNext(); ) {
            Operator value = iterator.next();
            if (value == operator) {
                haveFound = true;
                iterator.remove();
            }
        }
        return haveFound;
    }

    /**
     * Checks if operation is allowed.
     * 
     * @param operation The operation to be checked.
     * @return If operation is allowed, true if yes.
     */
    public boolean checkOperationValidity(Operator operation) {
        for (Operator value : allowedOperations) {
            if (value == operation) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if character is an operator.
     * 
     * @param character The operator to be checked.
     * @return If character is a operator.
     */
    public boolean isOperator(char character) {
        for (Operator value : allowedOperations) {
            if (value.getOperator().charAt(0) == character) {
                return true;
            }
        }

        return false;
    } 

    /**
     * Checks if string is an operator.
     * 
     * @param string The operator to be checked.
     * @return If string is a operator.
     */
    public boolean isOperator(String string) {
        for (Operator value : allowedOperations) {
            if (value.getOperator() == string) {
                return true;
            }
        }

        return false;
    }
}
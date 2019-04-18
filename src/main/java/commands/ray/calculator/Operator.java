package commands.ray.calculator;

/**
 * <h1>Enum of operations</h1>
 * <p>
 * The enums of all possible operations with some methods for identifying and getting the data of them.
 * <b>Note:</b> This class is only intended to be used by calculator and manager.
 *
 * @author Ray Z.
 * @since 2019-02-25
 */
public enum Operator {
    ADD("+", true, 1), SUBTRACT("-", true, 1), MULTIPLY("*", true, 2), DIVIDE("/", true, 2), EXPONENT("^", true, 3),
    SQAURE("sqrt", true, 3), // FACTORIAL("!", false, 4),
    OPEN_PARENTHESIS("_(", false, 0), CLOSE_PARENTHESIS("_(", false, 0); // The parentheses are special cases

    private String operator;
    private boolean isPrefix;
    private int precedence;

    /**
     * Constructor for the Operations enum.
     * 
     * @param operator The operator symbol used in a string.
     * @param isPrefix If the operator is calculated based on a prefix.
     * @param precedence The precedence of the operator.
     */
    Operator(String operator, boolean isPrefix, int precedence) {
        this.operator = operator;
        this.isPrefix = isPrefix;
        this.precedence = precedence;
    }

    /**
     * Checks if the operator is a parenthesis.
     * 
     * @param operator The operator to be evaluated.
     * @return If the operator is a parenthesis, true if yes.
     */
    protected boolean isParenthesis(Operator operator) {
        return operator.operator == "_(" || operator.operator == "_)";
    }

    /**
     * Gets the operator of the enum.
     * 
     * @return the operator of the enum.
     */
    protected String getOperator() {
        return operator;
    }

    /**
     * Checks if string is an operator.
     * 
     * @param string The string to be evaluated.
     * @return If string is an operator, true if yes.
     */
    protected static boolean isOperator(String string) {
        for (Operator operation : Operator.values()) {
            if (operation.getOperator().toLowerCase().equals(string.toLowerCase())) {
                if (operation.operator == "_(" || operation.operator == "_)") {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if character is an operator.
     * 
     * @param character The character to be checked.
     * @return If the character is an operator, true if yes.
     */
    protected boolean isOperator(char character) {
        for (Operator operation : Operator.values()) {
            if (operation.toString().toLowerCase().equals(Character.toString(character).toLowerCase())) {
                if (operation.operator == "_(" || operation.operator == "_)") {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the enum based on the operator.
     * 
     * @param operator The operator to be evaluated.
     * @return The enum of the operator, null if operator is not found.
     */
    protected static Operator getEnumFromOperator(String operator) throws IllegalArgumentException {
        for (Operator operation : Operator.values()) {
            if (operation.operator.toLowerCase().equals(operator.toLowerCase())) {
                return operation;
            }
        }
        
        return null;
    }

    /**
     * Gets if the operator is a prefix.
     * 
     * @return If the operator is a prefix, true if yes.
     */
    protected boolean getPrefix() {
        return isPrefix;
    }

    /**
     * Checks if operator in form of a string is a prefix.
     * 
     * @param string The string of the operator's operator, for example the plus sign (+).
     * @return If operator is a prefix, true if yes.
     */
    protected static boolean isPrefix(String string) {
        Operator operator;
        try {
            operator = getEnumFromOperator(string);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return operator.getPrefix();
    }

    /**
     * Gets the precedence of the operator.
     * 
     * @return The precedence of the operator.
     */
    protected int getPrecedence() {
        return precedence;
    }
}
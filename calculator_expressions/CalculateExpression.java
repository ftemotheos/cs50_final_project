package calculator_expressions;

import java.util.Scanner;

public final class CalculateExpression {

    public static void main(String ... args) {
        String incomingString;
        if (args.length == 1 && args[0] != null) {
            System.out.print("Incoming arithmetic expression is:\n> ");
            incomingString = args[0];
            System.out.println(incomingString);
            calculate(incomingString);
        }
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter arbitrary arithmetic expression:\n> ");
            while(!(incomingString = scanner.nextLine()).equalsIgnoreCase("quite")) {
                calculate(incomingString);
                System.out.print("> ");
            }
            scanner.close();
        }
    }

    public static void calculate(String incomingString) {
        ArithmeticExpressionBuilder builder = new ArithmeticExpressionBuilder();
        ArithmeticExpression expression;
        try {
            expression = builder.buildExpression(incomingString);
        } catch (LexicalArithmeticException e) {
            System.err.println("LEXICAL_ERROR: " + e.getMessage());
            return;
        } catch (SyntaxArithmeticException e) {
            System.err.println("SYNTAX_ERROR: " + e.getMessage());
            return;
        }
        ExpressionContext context = new ExpressionContext();
        try {
            expression.interpret(context);
        } catch (SemanticArithmeticException e) {
            System.err.println("SEMANTIC_ERROR: " + e.getMessage());
            System.err.println("Because of:\n" + e.getCause().toString());
            return;
        }
        System.out.println("The result of the calculation of expression is:\n< " + expression.getResult());
    }

}

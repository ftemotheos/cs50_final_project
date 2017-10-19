package calculator_expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ArithmeticExpressionBuilder implements ExpressionBuilder<ArithmeticExpression> {

    private ArithmeticParser parser;

    private ArithmeticToken token;

    @Override
    public ArithmeticExpression buildExpression(String stringExpression) {
        parser = new ArithmeticParser(stringExpression);
        token = new ArithmeticToken();
        return evaluate();
    }

    private ArithmeticExpression evaluate() {
        parser.nextToken();
        if (token.isEndOfExpressionCurrentState()) {
            throw new LexicalArithmeticException("ArithmeticExpressionBuilder: The incoming expression is empty!");
        }
        ArithmeticExpression result = evaluateNonTerminalExpressionWithFifthPriority();
        if (!(token.isEndOfExpressionCurrentState())) {
            throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Incorrect format of expression!");
        }
        return result;
    }

    private ArithmeticExpression evaluateNonTerminalExpressionWithFifthPriority() {
        char op;
        ArithmeticExpression result;
        ArithmeticExpression partialResult;
        result = evaluateNonTerminalExpressionWithForthPriority();
        while (token.isOperationCurrentState() && ((op = token.getValue().charAt(0)) == '+' || op == '-')) {
            parser.nextToken();
            partialResult = evaluateNonTerminalExpressionWithForthPriority();
            switch (op) {
                case '+':
                    result =  new AdditionExpression(result, partialResult);
                    break;
                case '-':
                    result = new SubtractionExpression(result, partialResult);
                    break;
            }
        }
        return result;
    }

    private ArithmeticExpression evaluateNonTerminalExpressionWithForthPriority() {
        char op;
        ArithmeticExpression result;
        ArithmeticExpression partialResult;
        result = evaluateNonTerminalExpressionWithThirdPriority();
        while (token.isOperationCurrentState() && ((op = token.getValue().charAt(0)) == '*' || op == '/' || op == '%')) {
            parser.nextToken();
            partialResult = evaluateNonTerminalExpressionWithThirdPriority();
            switch (op) {
                case '*':
                    result =  new MultiplicationExpression(result, partialResult);
                    break;
                case '/':
                    result = new DivisionExpression(result, partialResult);
                    break;
                case '%':
                    result = new RemainderOfTheDivisionExpression(result, partialResult);
            }
        }
        return result;
    }

    private ArithmeticExpression evaluateNonTerminalExpressionWithThirdPriority() {
        ArithmeticExpression result;
        ArithmeticExpression partialResult;
        result = evaluateNonTerminalExpressionWithSecondPriority();
        if (token.isOperationCurrentState() && token.getValue().equals("^")) {
            parser.nextToken();
            partialResult = evaluateNonTerminalExpressionWithThirdPriority();
            result = new DegreeExpression(result, partialResult);
        }
        return result;
    }

    private ArithmeticExpression evaluateNonTerminalExpressionWithSecondPriority() {
        char op = '+';
        ArithmeticExpression result;
        if (token.isOperationCurrentState() && ((op = token.getValue().charAt(0)) == '+' || op == '-')) {
            if (token.isNonePreviousState() || token.isOperationPreviousState()) {
                parser.nextToken();
            }
        }
        result = evaluateNonTerminalExpressionWithFirstPriority();
        if (op == '-') {
            result = new NegateExpression(result);
        }
        return result;
    }

    private ArithmeticExpression evaluateNonTerminalExpressionWithFirstPriority() {
        ArithmeticExpression result;
        if (token.isOperationCurrentState()) {
            if (token.getValue().equals("(")) {
                parser.nextToken();
                if (token.getValue().equals(")")) {
                    result = new ConstantExpression(0);
                }
                else {
                    result = evaluateNonTerminalExpressionWithFifthPriority();
                }
                if (!token.getValue().equals(")")) {
                    throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Missing ')' in expression!");
                }
                parser.nextToken();
            }
            else if (token.getValue().equals("|")) {
                parser.nextToken();
                result = evaluateNonTerminalExpressionWithFifthPriority();
                result = new AbsoluteExpression(result);
                if (!token.getValue().equals("|")) {
                    throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Missing '|' in expression!");
                }
                parser.nextToken();
            }
            else {
                throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Unsupported operation '" + token.getValue() + "' in expression!");
            }
        }
        else if (token.isFunctionCurrentState()) {
            List<String> parameters = token.getParameters();
            ArithmeticExpressionBuilder subBuilder = new ArithmeticExpressionBuilder();
            if (parameters.size() > 0) {
                ArithmeticExpression[] partialResults = new ArithmeticExpression[parameters.size()];
                Iterator<String> parametersIterator = parameters.iterator();
                String parameter;
                for (int i = 0; i < partialResults.length; i++) {
                    if (!(parameter = parametersIterator.next()).equals("")) {
                        partialResults[i] = subBuilder.buildExpression(parameter);
                    }
                    else {
                        parametersIterator.remove();
                    }
                }
                if (token.getValue().equalsIgnoreCase("max")) {
                    result = new MaximumExpression(partialResults);
                }
                else if (token.getValue().equalsIgnoreCase("min")) {
                    result = new MinimumExpression(partialResults);
                }
                else if (parameters.size() == 1) {
                    if (token.getValue().equalsIgnoreCase("round")) {
                        result = new RoundExpression(partialResults[0]);
                    }
                    else if (token.getValue().equalsIgnoreCase("sqrt")) {
                        result = new SquareRootExpression(partialResults[0]);
                    }
                    else if (token.getValue().equalsIgnoreCase("cbrt")) {
                        result = new CubicRootExpression(partialResults[0]);
                    }
                    else if (token.getValue().equalsIgnoreCase("sin")) {
                        result = new SineExpression(partialResults[0]);
                    }
                    else if (token.getValue().equalsIgnoreCase("cos")) {
                        result = new CosineExpression(partialResults[0]);
                    }
                    else if (token.getValue().equalsIgnoreCase("tg")) {
                        result = new TangentExpression(partialResults[0]);
                    }
                    else {
                        throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Unsupported arithmetic function with 1 parameter in expression!");
                    }
                }
                else if (parameters.size() == 2) {
                    if (token.getValue().equalsIgnoreCase("log")) {
                        result = new LogarithmExpression(partialResults[0], partialResults[1]);
                    }
                    else {
                        throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Unsupported arithmetic function with 2 parameters in expression!");
                    }
                }
                else {
                    throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Unsupported arithmetic function with " + parameters.size() + " parameters in expression!");
                }
            }
            else {
                throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Unsupported arithmetic function without any parameters in expression!");
            }
            parser.nextToken();
        }
        else {
            result = evaluateTerminalExpression();
        }
        return result;
    }

    private ArithmeticExpression evaluateTerminalExpression() {
        ArithmeticExpression result;
        if (token.isNumberCurrentState()) {
            if (token.getValue().equalsIgnoreCase("E")) {
                result = new ConstantExpression(Math.E);
            }
            else if (token.getValue().equalsIgnoreCase("PI")) {
                result = new ConstantExpression(Math.PI);
            }
            else {
                try {
                    result = new ConstantExpression(Double.parseDouble(token.getValue()));
                } catch (NumberFormatException e) {
                    throw new LexicalArithmeticException("ArithmeticExpressionBuilder: Incorrect format of the operand \"" + token.getValue() + "\" in expression! It must be a Java number!");
                }
            }
            parser.nextToken();
        }
        else {
            throw new SyntaxArithmeticException("ArithmeticExpressionBuilder: Incorrect format of expression - one(or more) operand(s) of the operation is absent!");
        }
        return result;
    }

    private class ArithmeticParser implements Parser {

        private String stringExpression;

        private int stringExpressionIndex;

        private StringBuilder buffer;

        public ArithmeticParser (String stringExpression) {
            this.stringExpression = stringExpression;
            this.stringExpressionIndex = 0;
            this.buffer = new StringBuilder();
        }

        @Override
        public void nextToken() {
            if (!token.isNoneCurrentState()) {
                token.setNoneState();
            }
            if (stringExpressionIndex == stringExpression.length()) {
                token.setEndOfExpressionState();
                return;
            }
            while (stringExpressionIndex < stringExpression.length() &&
                    Character.isWhitespace(stringExpression.charAt(stringExpressionIndex))) {
                ++stringExpressionIndex;
            }
            if (stringExpressionIndex == stringExpression.length()) {
                token.setEndOfExpressionState();
                return;
            }
            if (isDelimiter(stringExpression.charAt(stringExpressionIndex))) {
                token.setOperationState(String.valueOf(stringExpression.charAt(stringExpressionIndex)));
                ++stringExpressionIndex;
            }
            else if (Character.isDigit(stringExpression.charAt(stringExpressionIndex))) {
                while (!isDelimiter(stringExpression.charAt(stringExpressionIndex))) {
                    buffer.append(stringExpression.charAt(stringExpressionIndex));
                    ++stringExpressionIndex;
                    if (stringExpressionIndex >= stringExpression.length()) {
                        break;
                    }
                }
                token.setNumberState(buffer.toString().trim());
                buffer.delete(0, buffer.length());
            }
            else if (Character.isLetter(stringExpression.charAt(stringExpressionIndex))) {
                while (!isDelimiter(stringExpression.charAt(stringExpressionIndex))) {
                    buffer.append(stringExpression.charAt(stringExpressionIndex));
                    ++stringExpressionIndex;
                    if (stringExpressionIndex >= stringExpression.length()) {
                        break;
                    }
                }
                if (stringExpressionIndex < stringExpression.length() && stringExpression.charAt(stringExpressionIndex) == '(') {
                    ++stringExpressionIndex;
                    if (stringExpressionIndex >= stringExpression.length()) {
                        throw new SyntaxArithmeticException("ArithmeticParser: Incorrect format of arithmetic function - parameter(s) and \')\' are absent!");
                    }
                    String functionName = buffer.toString().trim();
                    buffer.delete(0, buffer.length());
                    List<String> parameters = new ArrayList<>();
                    int openingParenthesesAmount = 0;
                    int closingParenthesesAmount = 0;
                    while (!(openingParenthesesAmount == closingParenthesesAmount && stringExpression.charAt(stringExpressionIndex) == ')')) {
                        if (stringExpression.charAt(stringExpressionIndex) == '(') {
                            ++openingParenthesesAmount;
                        }
                        if (stringExpression.charAt(stringExpressionIndex) == ')') {
                            ++closingParenthesesAmount;
                        }
                        if (openingParenthesesAmount == closingParenthesesAmount && stringExpression.charAt(stringExpressionIndex) == ',') {
                            parameters.add(buffer.toString());
                            buffer.delete(0, buffer.length());
                            ++stringExpressionIndex;
                            if (stringExpressionIndex >= stringExpression.length()) {
                                throw new SyntaxArithmeticException("ArithmeticParser: Incorrect format of arithmetic function - parameter(s) and(or) \')\' are absent!");
                            }
                        }
                        buffer.append(stringExpression.charAt(stringExpressionIndex));
                        ++stringExpressionIndex;
                        if (stringExpressionIndex >= stringExpression.length()) {
                            throw new SyntaxArithmeticException("ArithmeticParser: Incorrect format of arithmetic function - parameter(s) and(or) \')\' are absent!");
                        }
                    }
                    parameters.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                    token.setFunctionState(functionName, parameters);
                    ++stringExpressionIndex;
                }
                else {
                    String constantName = buffer.toString().trim();
                    buffer.delete(0, buffer.length());
                    if (constantName.equalsIgnoreCase("E")) {
                        token.setNumberState(constantName);
                    }
                    else if (constantName.equalsIgnoreCase("PI")) {
                        token.setNumberState(constantName);
                    }
                    else {
                        throw new LexicalArithmeticException("ArithmeticParser: Unknown constant name \"" + constantName + "\"!");
                    }
                }
            }
            else {
                throw new LexicalArithmeticException("ArithmeticParser: Unknown incoming token!");
            }
        }

        private boolean isDelimiter(char c) {
            return "+-*/%^()|".indexOf(c) != -1;
        }

    }

}

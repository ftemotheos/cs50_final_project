package calculator_expressions;

import java.util.List;

public final class ArithmeticToken implements Token {

    private ArithmeticTokenState noneState;

    private ArithmeticTokenState operationState;

    private ArithmeticTokenState numberState;

    private ArithmeticTokenState functionState;

    private ArithmeticTokenState endOfExpressionState;

    private ArithmeticTokenState currentState = noneState;

    private ArithmeticTokenState previousState = noneState;

    public ArithmeticToken() {
        noneState = new NoneState();
        endOfExpressionState = new EndOfExpressionState();
    }

    public void setNoneState() {
        previousState = currentState;
        currentState = noneState;
    }

    public void setOperationState(String value) {
        previousState = currentState;
        currentState = operationState = new OperationState(value);
    }

    public void setNumberState(String value) {
        previousState = currentState;
        currentState = numberState = new NumberState(value);
    }

    public void setFunctionState(String value, List<String> parameters) {
        previousState = currentState;
        currentState = functionState = new FunctionState(value, parameters);
    }

    public void setEndOfExpressionState() {
        previousState = currentState;
        currentState = endOfExpressionState;
    }

    public boolean isNoneCurrentState() {
        return currentState == noneState;
    }

    public boolean isOperationCurrentState() {
        return currentState == operationState;
    }

    public boolean isNumberCurrentState() {
        return currentState == numberState;
    }

    public boolean isFunctionCurrentState() {
        return currentState == functionState;
    }

    public boolean isEndOfExpressionCurrentState() {
        return currentState == endOfExpressionState;
    }

    public boolean isNonePreviousState() {
        return previousState == noneState;
    }

    public boolean isOperationPreviousState() {
        return previousState == operationState;
    }

    public boolean isNumberPreviousState() { return previousState == numberState; }

    public boolean isFunctionPreviousState() { return previousState == functionState; }

    public boolean isEndOfExpressionPreviousState() { return previousState == endOfExpressionState; }

    @Override
    public String getValue() {
        return currentState.getValue();
    }

    public List<String> getParameters() {
        List<String> result;
        if (isFunctionCurrentState()) {
            result = ((FunctionState)currentState).getParameters();
        }
        else throw new UnsupportedOperationException();
        return result;
    }

}

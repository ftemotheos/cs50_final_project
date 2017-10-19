package calculator_expressions;

import java.util.List;

public final class FunctionState extends ArithmeticTokenState {

    private List<String> parameters;

    public FunctionState(String value, List<String> parameters) {
        this.value = value;
        this.parameters = parameters;
    }

    public List<String> getParameters() {
        return parameters;
    }

}

package calculator_expressions;

import java.util.HashMap;
import java.util.Map;

public final class ExpressionContext implements Context<Expression, Number> {

    private Map<Expression, Number> context = new HashMap<>();

    public Number getContext(Expression name) {
        return context.get(name);
    }

    public void addContext(Expression name, Number value) {
        context.put(name, value);
    }

}

package calculator_expressions;

public final class MinimumExpression extends ArithmeticExpression {

    private ArithmeticExpression[] expressions;

    public MinimumExpression(ArithmeticExpression ... expressions) {
        this.expressions = expressions;
    }

    public void interpret(ExpressionContext context) {
        value = Double.POSITIVE_INFINITY;
        try {
            for (ArithmeticExpression expression: expressions) {
                expression.interpret(context);
                value = Math.min(value.doubleValue(), context.getContext(expression).doubleValue());
            }
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("MinimumExpression: It's not possible to interpret the expression!", e);
        }
    }

}

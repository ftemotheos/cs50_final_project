package calculator_expressions;

public final class MaximumExpression extends ArithmeticExpression {

    private ArithmeticExpression[] expressions;

    public MaximumExpression(ArithmeticExpression ... expressions) {
        this.expressions = expressions;
    }

    public void interpret(ExpressionContext context) {
        value = Double.NEGATIVE_INFINITY;
        try {
            for (ArithmeticExpression expression: expressions) {
                expression.interpret(context);
                value = Math.max(value.doubleValue(), context.getContext(expression).doubleValue());
            }
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("MaximumExpression: It's not possible to interpret the expression!", e);
        }
    }

}

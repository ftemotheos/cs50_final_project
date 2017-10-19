package calculator_expressions;

public final class CubicRootExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public CubicRootExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.cbrt(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("CubicRootExpression: It's not possible to interpret the expression!", e);
        }
    }

}

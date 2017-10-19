package calculator_expressions;

public final class RoundExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public RoundExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.round(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("RoundExpression: It's not possible to interpret the expression!", e);
        }
    }

}

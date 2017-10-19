package calculator_expressions;

public final class SineExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public SineExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.sin(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("SineExpression: It's not possible to interpret the expression!", e);
        }
    }

}

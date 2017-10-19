package calculator_expressions;

public final class NegateExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public NegateExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = -context.getContext(expressionA).doubleValue();
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("NegateExpression: It's not possible to interpret the expression!", e);
        }
    }

}

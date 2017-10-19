package calculator_expressions;

public final class TangentExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public TangentExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.tan(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("TangentExpression: It's not possible to interpret the expression!", e);
        }
    }

}

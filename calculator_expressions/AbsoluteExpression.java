package calculator_expressions;

public final class AbsoluteExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public AbsoluteExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.abs(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("AbsoluteExpression: It's not possible to interpret the expression!", e);
        }
    }

}

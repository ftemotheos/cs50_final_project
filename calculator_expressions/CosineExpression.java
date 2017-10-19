package calculator_expressions;

public final class CosineExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public CosineExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.cos(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("CosineExpression: It's not possible to interpret the expression!", e);
        }
    }

}

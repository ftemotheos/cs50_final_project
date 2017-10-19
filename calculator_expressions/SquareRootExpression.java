package calculator_expressions;

public final class SquareRootExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    public SquareRootExpression(ArithmeticExpression expressionA) {
        this.expressionA = expressionA;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            value = Math.sqrt(context.getContext(expressionA).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("SquareRootExpression: It's not possible to interpret the expression!", e);
        }
    }

}

package calculator_expressions;

public final class SubtractionExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    private ArithmeticExpression expressionB;

    public SubtractionExpression(ArithmeticExpression expressionA, ArithmeticExpression expressionB) {
        this.expressionA = expressionA;
        this.expressionB = expressionB;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            expressionB.interpret(context);
            value = context.getContext(expressionA).doubleValue() - context.getContext(expressionB).doubleValue();
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("SubtractionExpression: It's not possible to interpret the expression!", e);
        }
    }

}

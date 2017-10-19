package calculator_expressions;

public final class RemainderOfTheDivisionExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    private ArithmeticExpression expressionB;

    public RemainderOfTheDivisionExpression(ArithmeticExpression expressionA, ArithmeticExpression expressionB) {
        this.expressionA = expressionA;
        this.expressionB = expressionB;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            expressionB.interpret(context);
            value = context.getContext(expressionA).doubleValue() % context.getContext(expressionB).doubleValue();
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("RemainderOfTheDivisionExpression: It's not possible to interpret the expression!", e);
        }
    }

}

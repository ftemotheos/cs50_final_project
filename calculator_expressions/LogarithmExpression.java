package calculator_expressions;

public final class LogarithmExpression extends ArithmeticExpression {

    private ArithmeticExpression expressionA;

    private ArithmeticExpression expressionB;

    public LogarithmExpression(ArithmeticExpression expressionA, ArithmeticExpression expressionB) {
        this.expressionA = expressionA;
        this.expressionB = expressionB;
    }

    public void interpret(ExpressionContext context) {
        try {
            expressionA.interpret(context);
            expressionB.interpret(context);
            value = Math.log(context.getContext(expressionA).doubleValue()) / Math.log(context.getContext(expressionB).doubleValue());
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("LogarithmExpression: It's not possible to interpret the expression!", e);
        }
    }

}

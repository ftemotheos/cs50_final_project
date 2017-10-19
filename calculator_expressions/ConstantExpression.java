package calculator_expressions;

public final class ConstantExpression extends ArithmeticExpression {

    public ConstantExpression(Number value) {
        this.value = value;
    }

    public void interpret(ExpressionContext context) {
        try {
            context.addContext(this, value);
        } catch (RuntimeException e) {
            throw new SemanticArithmeticException("ConstantExpression: It's not possible to interpret the expression!", e);
        }
    }

}

package calculator_expressions;

public abstract class ArithmeticExpression implements Expression<ExpressionContext> {

    protected Number value;

    protected Number getResult() {
        return value;
    }

    public abstract void interpret(ExpressionContext context);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isInstance(o)) {
            return false;
        }
        ArithmeticExpression that = (ArithmeticExpression) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}

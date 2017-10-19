package calculator_expressions;

public interface ExpressionBuilder<T extends Expression> {

    T buildExpression(String stringExpression);

}

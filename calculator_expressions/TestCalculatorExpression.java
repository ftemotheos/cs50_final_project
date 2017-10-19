package calculator_expressions;

public final class TestCalculatorExpression {

    public static void main(String[] args) {
        CalculateExpression.main("max(-1, -5, log(1,5), cbrt(64), 2)");
        CalculateExpression.main("min(9, 4, 7, sqrt(64), log(8,2), 6)");
        CalculateExpression.main("1 / (cos(PI/6) + sin(PI/4) / tg(3 * PI / 2))");
        CalculateExpression.main("round(-5 ^ 8 / (4 + 6) - 2 * 6 ^ (1 / 7) - -1)");
        CalculateExpression.main("+90.99 % 77 - |-33| + E ^ log(5,E)");
    }

}

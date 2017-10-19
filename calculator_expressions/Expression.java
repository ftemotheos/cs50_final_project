package calculator_expressions;

public interface Expression<T extends Context> {

    void interpret(T context);

}

package calculator_expressions;

public interface Context<K, V> {

    V getContext(K name);

    void addContext(K name,V value);

}

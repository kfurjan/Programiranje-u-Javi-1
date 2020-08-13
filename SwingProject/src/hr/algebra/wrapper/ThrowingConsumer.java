package hr.algebra.wrapper;

/**
 * @author Kevin Furjan
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;
}

package info.pionas.rental.domain.space;

public class SquareMeterException extends RuntimeException {
    public SquareMeterException() {
        super("Square meter lower or equal zero");
    }
}

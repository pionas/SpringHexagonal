package info.pionas.rental.domain.space;

public class NotEnoughSpacesGivenException extends RuntimeException {
    private NotEnoughSpacesGivenException(String message) {
        super(message);
    }

    public static RuntimeException noSpaces() {
        return new NotEnoughSpacesGivenException("No spaces given");
    }
}

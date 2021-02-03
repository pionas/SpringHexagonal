package info.pionas.rental.domain.apartmentoffer;

public class ApartmentOfferNotFoundException extends RuntimeException {
    public ApartmentOfferNotFoundException(String apartmentId) {
        super("Offer for apartment with id: " + apartmentId + " does not exist");
    }
}
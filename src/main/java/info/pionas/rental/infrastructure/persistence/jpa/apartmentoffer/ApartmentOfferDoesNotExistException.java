package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

class ApartmentOfferDoesNotExistException extends RuntimeException {

    ApartmentOfferDoesNotExistException(String apartmentOfferId) {
        super("Apartment offer with id " + apartmentOfferId + " does not exist");
    }
}


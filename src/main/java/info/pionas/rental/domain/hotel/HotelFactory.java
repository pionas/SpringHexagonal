package info.pionas.rental.domain.hotel;

/**
 *
 * @author Adi
 */
public class HotelFactory {

    public Hotel create(String name, String street, String postalCode, String buildingNumber, String city, String country) {
        Address address = new Address(street, postalCode, buildingNumber, city, country);
        return new Hotel(name, address);
    }
}

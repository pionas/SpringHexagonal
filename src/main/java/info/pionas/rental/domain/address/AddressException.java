package info.pionas.rental.domain.address;

public class AddressException extends RuntimeException {
    public AddressException(AddressDto addressDto) {
        super("Address: street: " + addressDto.getStreet() + ", postalCode: " + addressDto.getPostalCode()
                + ", buildingNumber: " + addressDto.getBuildingNumber() + ", city: " + addressDto.getCity()
                + ", country: " + addressDto.getCountry() + "  does not exist.");
    }
}

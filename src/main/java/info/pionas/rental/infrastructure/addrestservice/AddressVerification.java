package info.pionas.rental.infrastructure.addrestservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressVerification {
    private static final String VALID = "VALID";
    private String status;

    public boolean isValid() {
        return VALID.equals(getStatus());
    }
}

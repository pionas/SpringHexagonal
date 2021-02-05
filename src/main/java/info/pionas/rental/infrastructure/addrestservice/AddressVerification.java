package info.pionas.rental.infrastructure.addrestservice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressVerification {
    private static final String VALID = "VALID";
    private String status;

    public boolean isValid() {
        return VALID.equals(status);
    }
}

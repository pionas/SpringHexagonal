package info.pionas.rental.domain.apartmentoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class ApartmentOffer {
    private final String apartmentId;
    private final Money money;
    private final ApartmentAvailability availability;

    public static class Builder {

        private String apartmentId;
        private BigDecimal price;
        private LocalDate start;
        private LocalDate end;

        public static Builder apartmentOffer() {
            return new Builder();
        }

        public Builder withApartmentId(String apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withAvailability(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
            return this;
        }

        public ApartmentOffer build() {
            return new ApartmentOffer(apartmentId, money(), availability());
        }

        private Money money() {
            return Money.of(price);
        }

        private ApartmentAvailability availability() {
            return ApartmentAvailability.of(start, end);
        }

    }
}

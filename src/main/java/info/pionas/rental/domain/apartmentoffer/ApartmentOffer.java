package info.pionas.rental.domain.apartmentoffer;

import info.pionas.rental.domain.money.Money;

import info.pionas.rental.domain.period.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "APARTMENT_OFFER")
public class ApartmentOffer {
    @Id
    @GeneratedValue
    private UUID id;
    private String apartmentId;
    @Embedded
    private Money money;
    @Embedded
    private Period availability;

    public String id() {
        return id.toString();
    }

    static class Builder {

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
            return new ApartmentOffer(null, apartmentId, money(), availability());
        }

        private Money money() {
            return Money.of(price);
        }

        private Period availability() {
            return Period.from(start, end);
        }

    }
}

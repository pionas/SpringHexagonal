package info.pionas.rental.domain.apartmentoffer;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.offeravailability.OfferAvailability;
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
    private OfferAvailability availability;

    private ApartmentOffer(String apartmentId, Money money, OfferAvailability availability) {
        this.apartmentId = apartmentId;
        this.money = money;
        this.availability = availability;
    }

    public UUID id() {
        return id;
    }

    public boolean hasAvailabilityWithin(Period period) {
        return availability.coversAllDaysWithin(period);
    }

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

        private OfferAvailability availability() {
            return OfferAvailability.from(start, end);
        }

        private Money money() {
            return Money.of(price);
        }

    }
}

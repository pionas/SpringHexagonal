package info.pionas.rental.domain.agreement;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "AGREEMENT")
public class Agreement {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;
    private String rentalPlaceId;
    private String ownerId;
    private String tenantId;
    @ElementCollection
    private List<LocalDate> days;
    @Embedded
    private Money price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Agreement agreement = (Agreement) o;

        return new EqualsBuilder()
                .append(getRentalType(), agreement.rentalType)
                .append(getRentalPlaceId(), agreement.rentalPlaceId)
                .append(getOwnerId(), agreement.ownerId)
                .append(getTenantId(), agreement.tenantId)
                .append(getDays(), agreement.days)
                .append(getPrice(), agreement.price)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getRentalType())
                .append(getRentalPlaceId())
                .append(getOwnerId())
                .append(getTenantId())
                .append(getDays())
                .append(getPrice())
                .toHashCode();
    }

    public static class Builder {
        private RentalType rentalType;
        private String rentalPlaceId;
        private String ownerId;
        private String tenantId;
        private Money price;
        private List<LocalDate> days;

        private Builder() {
        }

        public static Builder agreement() {
            return new Builder();
        }

        public Builder withRentalType(RentalType rentalType) {
            this.rentalType = rentalType;
            return this;
        }

        public Builder withRentalPlaceId(String rentalPlaceId) {
            this.rentalPlaceId = rentalPlaceId;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder withDays(List<LocalDate> days) {
            this.days = days;
            return this;
        }

        public Builder withPrice(Money price) {
            this.price = price;
            return this;
        }

        public Agreement build() {
            return new Agreement(null, rentalType, rentalPlaceId, ownerId, tenantId, days, price);
        }
    }
}
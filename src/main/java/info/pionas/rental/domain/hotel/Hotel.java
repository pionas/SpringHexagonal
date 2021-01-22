package info.pionas.rental.domain.hotel;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "HOTEL")
public class Hotel {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Embedded
    private Address address;

    public Hotel(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String id() {
        return id.toString();
    }
}

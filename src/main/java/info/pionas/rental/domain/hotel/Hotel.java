package info.pionas.rental.domain.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
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

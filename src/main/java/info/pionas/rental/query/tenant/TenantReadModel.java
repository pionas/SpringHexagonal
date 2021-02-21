package info.pionas.rental.query.tenant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "TENANT")
public class TenantReadModel extends RepresentationModel<TenantReadModel> {
    @Id
    @GeneratedValue
    private UUID id;

    private String login;

    private String email;

    private String firstName;

    private String lastName;

}

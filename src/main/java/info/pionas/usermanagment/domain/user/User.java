package info.pionas.usermanagment.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "APARTMENT")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String login;
    @Embedded
    private Name name;

    public String id() {
        if (id == null) {
            return null;
        }
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .append(login, user.login)
                .append(name, user.name)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .append(name)
                .toHashCode();
    }

}

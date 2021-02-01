package info.pionas.usermanagment.domain.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class User {
    private final String login;
    private final Name name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder().append(login, user.login).append(name, user.name).isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(login).append(name).toHashCode();
    }

}

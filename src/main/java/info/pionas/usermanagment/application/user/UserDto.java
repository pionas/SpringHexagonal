package info.pionas.usermanagment.application.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDto {
    private final String login;
    private final String name;
    private final String lastName;

}

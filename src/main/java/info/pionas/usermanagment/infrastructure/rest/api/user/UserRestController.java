package info.pionas.usermanagment.infrastructure.rest.api.user;

import info.pionas.usermanagment.application.user.UserApplicationService;
import info.pionas.usermanagment.application.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserApplicationService userApplicationService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody UserDto userDto) {
        String userId = userApplicationService.register(userDto);
        return ResponseEntity.created(URI.create("/user/" + userId)).build();
    }
}

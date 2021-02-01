package info.pionas.usermanagment.infrastructure.rest.api.user;

import info.pionas.rental.application.hotelroomoffer.HotelRoomOfferDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.usermanagment.application.user.UserDto;
import info.pionas.usermanagment.infrastructure.persistence.jpa.user.SpringJpaUserTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class UserRestControllerSystemTest {
    private static final String LOGIN_1 = "john.doe";
    private static final String NAME_1 = "John";
    private static final String LAST_NAME_1 = "Doe";
    private final JsonFactory jsonFactory = new JsonFactory();
    @Autowired
    private SpringJpaUserTestRepository springJpaUserTestRepository;
    @Autowired
    private MockMvc mockMvc;

    private final List<String> userIds = new ArrayList<>();

    @AfterEach
    void deleteUsers() {
        springJpaUserTestRepository.deleteAll(userIds);
    }


    @Test
    void shouldSaveUser() throws Exception {
        UserDto userDto = givenUser1();
        getUserId(userDto);
    }

    @Test
    void shouldThrowExceptionWhenExistingUser() throws Exception {
        UserDto userDto = givenUser1();
        getUserId(userDto);

        Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(userDto)))
                    .andExpect(status().isCreated());
        });
    }

    private String getUserId(UserDto userDto) throws Exception {
        String url = save(userDto).getResponse().getRedirectedUrl();
        String userId = url.replace("/user/", "");
        userIds.add(userId);
        return userId;
    }

    private MvcResult save(UserDto userDto) throws Exception {
        return mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(userDto)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    private UserDto givenUser1() {
        return new UserDto(LOGIN_1, NAME_1, LAST_NAME_1);
    }
}
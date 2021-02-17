package info.pionas.rental.infrastructure.rest.api.tenant;

import info.pionas.rental.application.tenant.TenantDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.tenant.SpringJpaTenantTestRepository;
import org.junit.jupiter.api.AfterEach;
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
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class TenantRestControllerSystemTest {
    private static final String LOGIN_1 = "john.doe";
    private static final String FIRST_NAME_1 = "John";
    private static final String LAST_NAME_1 = "Doe";
    private static final String EMAIL_1 = "john.doe@example.com";
    private static final String LOGIN_2 = "jane.doe";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Doe";
    private static final String EMAIL_2 = "jane.doe@example.com";
    private static final String PASSWORD = "123456";

    private final JsonFactory jsonFactory = new JsonFactory();
    private final List<String> tenantIds = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringJpaTenantTestRepository tenantRepository;

    @AfterEach
    void deleteApartments() {
        tenantRepository.deleteAll(tenantIds);
    }

    @Test
    void shouldReturnAllTenant() throws Exception {
        save(givenTenant1());
        save(givenTenant2(null));

        mockMvc.perform(get("/tenant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldReturnTenantDoesNotExist() throws Exception {
        mockMvc.perform(get("/tenant/" + UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.firstName").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist());
    }

    @Test
    void shouldReturnExistingTenant() throws Exception {
        TenantDto tenantDto = givenTenant1();

        MvcResult mvcResult = mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isCreated())
                .andReturn();

        tenantIds.add(getTenantId(mvcResult));
        mockMvc.perform(get(mvcResult.getResponse().getRedirectedUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(LOGIN_1))
                .andExpect(jsonPath("$.email").value(EMAIL_1))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME_1))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME_1));
    }

    @Test
    void shouldThrowExceptionWhenGivenUsernameAlreadyExist() throws Exception {
        TenantDto tenantDto = givenTenant1();

        MvcResult mvcResult = mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isCreated())
                .andReturn();

        tenantIds.add(getTenantId(mvcResult));

        mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("Login " + LOGIN_1 + " already exists")))
                .andExpect(jsonPath("$.errors", hasItem("Email " + EMAIL_1 + " already exists")));
    }

    @Test
    void shouldThrowExceptionWhenGivenPasswordNotMatches() throws Exception {
        TenantDto tenantDto = givenTenant(LOGIN_1, EMAIL_1, FIRST_NAME_1, LAST_NAME_1, PASSWORD, "asasa", null);
        mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Password not matches")));
    }


    @Test
    void shouldThrowExceptionWhenGivenInvalidTenantDto() throws Exception {
        TenantDto tenantDto = givenTenant("a", "a", "", "", "a", "a", null);
        mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(4)))
                .andExpect(jsonPath("$.errors", hasItem("Email must be a well-formed address")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a first name")))
                .andExpect(jsonPath("$.errors", hasItem("Login size must be between 3 and 250")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a last name")));
    }

    @Test
    void shouldUpdateTenant() throws Exception {
        TenantDto tenantDto = givenTenant1();

        MvcResult mvcResult = mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isCreated())
                .andReturn();
        String tenantId = getTenantId(mvcResult);
        tenantIds.add(tenantId);
        tenantDto = givenTenant2(PASSWORD);
        mockMvc.perform(put("/tenant/" + tenantId).contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteTenant() throws Exception {
        TenantDto tenantDto = givenTenant1();

        MvcResult mvcResult = mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(delete(mvcResult.getResponse().getRedirectedUrl()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldThrowExceptionWhenTryDeleteNotExistTenant() throws Exception {
        String randomTenantId = UUID.randomUUID().toString();
        mockMvc.perform(delete("/tenant/" + randomTenantId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Tenant with id: " + randomTenantId + " does not exist")));
    }

    private TenantDto givenTenant1() {
        return givenTenant(LOGIN_1, EMAIL_1, FIRST_NAME_1, LAST_NAME_1, PASSWORD, PASSWORD, null);
    }

    private TenantDto givenTenant2(String currentPassword) {
        return givenTenant(LOGIN_2, EMAIL_2, FIRST_NAME_2, LAST_NAME_2, PASSWORD, PASSWORD, currentPassword);
    }

    private TenantDto givenTenant(String login, String email, String firstName, String lastName, String password, String passwordRepeat, String currentPassword) {
        return new TenantDto(login, email, firstName, lastName, password, passwordRepeat, currentPassword);
    }


    private MvcResult save(TenantDto tenantDto) throws Exception {
        MvcResult result = mockMvc.perform(post("/tenant").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(tenantDto))).andReturn();
        tenantIds.add(getTenantId(result));

        return result;
    }

    private String getTenantId(MvcResult result) {
        return result.getResponse().getRedirectedUrl().replace("/tenant/", "");
    }
}
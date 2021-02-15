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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        save(givenTenant2());

        mockMvc.perform(get("/tenant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }


    private TenantDto givenTenant1() {
        return new TenantDto(LOGIN_1, EMAIL_1, FIRST_NAME_1, LAST_NAME_1, PASSWORD, PASSWORD, null);
    }

    private TenantDto givenTenant2() {
        return new TenantDto(LOGIN_2, EMAIL_2, FIRST_NAME_2, LAST_NAME_2, PASSWORD, PASSWORD, null);
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
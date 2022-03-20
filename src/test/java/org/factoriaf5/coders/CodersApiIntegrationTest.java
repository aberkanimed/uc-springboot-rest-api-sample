package org.factoriaf5.coders;

import org.factoriaf5.coders.domain.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CodersApiIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoderRepository coderRepository;

    @BeforeEach
    void clear() {
        coderRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        coderRepository.deleteAll();
    }

    @Test
    void returnsTheExistingCoders() throws Exception {

        addTestCoders();

        mockMvc.perform(get("/api/coders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName", equalTo("Mustafa")))
                .andExpect(jsonPath("$[0].emailAddress", equalTo("hhane@example.org")))
                .andExpect(jsonPath("$[1].firstName", equalTo("Shirley")))
                .andExpect(jsonPath("$[1].emailAddress", equalTo("maddison.donnelly@example.org")))
                .andExpect(jsonPath("$[2].firstName", equalTo("Roxanne")))
                .andExpect(jsonPath("$[2].emailAddress", equalTo("pfannerstill.arnold@example.com")))
                .andDo(print());
    }

    @Test
    void allowsToCreateANewCoder() throws Exception {
        mockMvc.perform(post("/api/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"Mohamed\", " +
                        "\"lastName\": \"Aberkani\", " +
                        "\"emailAddress\": \"john@doe.com\", " +
                        "\"country\": \"Spain\", " +
                        "\"course\": \"Java\" " +
                        "}")
        ).andExpect(status().isOk());

        List<Coder> coders = coderRepository.findAll();
        assertThat(coders, contains(allOf(
                hasProperty("firstName", is("Mohamed")),
                hasProperty("lastName", is("Aberkani")),
                hasProperty("emailAddress", is("john@doe.com")),
                hasProperty("country", is("Spain")),
                hasProperty("course", is("Java"))
        )));
    }

    @Test
    void allowsToFindACoderById() throws Exception {

        Coder coder = coderRepository.save(new Coder("Mohamed", "Aberkani", "hhane@example.org", "Cape Verde", "Java"));

        mockMvc.perform(get("/api/coders/" + coder.getCoderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Mohamed")))
                .andExpect(jsonPath("$.lastName", equalTo("Aberkani")))
                .andExpect(jsonPath("$.emailAddress", equalTo("hhane@example.org")))
                .andExpect(jsonPath("$.country", equalTo("Cape Verde")))
                .andExpect(jsonPath("$.course", equalTo("Java")));
    }

    @Test
    void returnsAnErrorIfTryingToGetACoderThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/coders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToDeleteACoderById() throws Exception {
        Coder coder = coderRepository.save(new Coder("Mohamed", "Aberkani", "hhane@example.org", "Cape Verde", "Java"));

        mockMvc.perform(delete("/api/coders/"+ coder.getCoderId()))
                .andExpect(status().isOk());

        List<Coder> coders = coderRepository.findAll();
        assertThat(coders, not(contains(allOf(
                hasProperty("firstName", is("Mohamed")),
                hasProperty("lastName", is("Aberkani"))
        ))));
    }

    @Test
    void returnsAnErrorIfTryingToDeleteACoderThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/coders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToModifyACoder() throws Exception {
        Coder coder = coderRepository.save(new Coder("Mohamed", "Aberkani", "hhane@example.org", "Cape Verde", "Java"));

        mockMvc.perform(put("/api/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"coderId\": \"" + coder.getCoderId() + "\", " +
                        "\"firstName\": \"Andrea\", " +
                        "\"lastName\": \"Cevallos\" }")
        ).andExpect(status().isOk());

        List<Coder> coders = coderRepository.findAll();

        assertThat(coders, hasSize(1));
        assertThat(coders.get(0).getFirstName(), equalTo("Andrea"));
        assertThat(coders.get(0).getLastName(), equalTo("Cevallos"));
    }

    @Test
    void returnsAnErrorWhenTryingToModifyACoderThatDoesNotExist() throws Exception {
        addTestCoders();

        mockMvc.perform(put("/api/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"coderId\": \"" + -1 + "\", " +
                        "\"firstName\": \"John\", " +
                        "\"lastName\": \"Doe\" }")
        ).andExpect(status().isNotFound());
    }

    private void addTestCoders() {
        List<Coder> coders = List.of(
                new Coder("Mustafa", "Kozey", "hhane@example.org", "Cape Verde", "id"),
                new Coder("Shirley", "Fay", "maddison.donnelly@example.org", "Cuba", "temporibus"),
                new Coder("Roxanne", "Kovacek", "pfannerstill.arnold@example.com", "Latvia", "esse")
        );

        coders.forEach(coderRepository::save);
    }
}
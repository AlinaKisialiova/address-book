package ie.akisialiova.addressbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.repository.PersonRepository;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean(answer = Answers.RETURNS_MOCKS)
    private PersonRepository repository;

    @Test
    public void whenPostRequestToPersonsAndValidPerson_thenCorrectResponse() throws Exception {
        mockMvc.perform(post(PersonController.PERSONS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Person("John", "O'Neill")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()));
    }

    @Test
    public void whenPostRequestToPersonsAndInValidPerson_thenCorrectResponse() throws Exception {
        mockMvc.perform(post(PersonController.PERSONS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Person("John", ""))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("last name is mandatory")));
    }

    @Test
    public void whenPutRequestToPersonsAndPerson_thenCorrectResponse() throws Exception {
        mockMvc.perform(put(PersonController.PERSONS_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Person("John", "MacNeill")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPutRequestToPersonsAndInvalidPerson_thenCorrectResponse() throws Exception {
        mockMvc.perform(post(PersonController.PERSONS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Person("John", "")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenDeleteRequestToPersons_thenCorrectResponse() throws Exception {
        mockMvc.perform(delete(PersonController.PERSONS_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void whenGetAllRequestToPersons_thenCorrectResponse() throws Exception {
        mockMvc.perform(get(PersonController.PERSONS_PATH + "?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()));
    }

    @Test
    public void whenGetTotalRequestToPersons_thenCorrectResponse() throws Exception {
        mockMvc.perform(get(PersonController.PERSONS_PATH + "/total")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}

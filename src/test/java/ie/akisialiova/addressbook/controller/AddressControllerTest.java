package ie.akisialiova.addressbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.service.AddressService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    public static final String PERSON_ADDRESS_PATH = AddressController.ADDRESSES_PATH.replace("{personId}", "1");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddressService service;

    @Test
    public void whenPostRequestToAddressAndValidAddress_thenCorrectResponse() throws Exception {
        Address address = Address.builder()
                .street("O'Conell")
                .state("co.Dublin")
                .postalCode("123X45")
                .city("Dublin")
                .build();
        mockMvc.perform(post(PERSON_ADDRESS_PATH)
                .content(mapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()));
    }

    @Test
    public void whenPostRequestToAddressAndInValidAddress_thenCorrectResponse() throws Exception {
        Address address = new Address();
        mockMvc.perform(post(PERSON_ADDRESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("postal code is mandatory")));
    }

    @Test
    public void whenPutRequestToAddressAndValidAddress_thenCorrectResponse() throws Exception {
        Address address = Address.builder()
                .id(1L)
                .street("O'Conell")
                .state("co.Dublin")
                .postalCode("123X45")
                .city("Dublin")
                .build();
        mockMvc.perform(put(PERSON_ADDRESS_PATH + "/1")
                .content(mapper.writeValueAsString(address))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()));
    }

    @Test
    public void whenEditRequestToAddressAndInValidAddress_thenCorrectResponse() throws Exception {
        Address address = new Address();
        mockMvc.perform(put(PERSON_ADDRESS_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(address)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()));
    }

    @Test
    public void whenDeleteRequestToAddress_thenCorrectResponse() throws Exception {
        mockMvc.perform(delete(PERSON_ADDRESS_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

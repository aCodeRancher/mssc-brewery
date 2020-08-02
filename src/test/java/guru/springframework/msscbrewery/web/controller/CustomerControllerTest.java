package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerDto customerDto;
    CustomerDto customerDtoValid;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUp(){
         customerDto = CustomerDto.builder()
                       .id(UUID.randomUUID())
                       .name("").build();
         customerDtoValid = CustomerDto.builder()
                            .id(UUID.randomUUID())
                            .name("John").build();
    }

    @Test
    public void handlePost_error() throws Exception{
        String customerDtoJson = mapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customer")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(customerDtoJson))
                .andExpect(status().isBadRequest());
     }

     @Test
    public void handlePost() throws Exception{
        String customerDtoJson = mapper.writeValueAsString(customerDtoValid);
        when(customerService.saveNewCustomer(any(CustomerDto.class))).thenReturn(customerDtoValid);
        mockMvc.perform(post("/api/v1/customer")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(customerDtoJson))
                 .andExpect(status().isCreated());
        verify(customerService,times(1)).saveNewCustomer(any(CustomerDto.class));
     }
}
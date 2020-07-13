package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto customerDto;

    private UUID uuid ;

    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID();
        customerDto = CustomerDto.builder()
                .id(uuid)
                .name("John")
                .build();
    }

    @Test
    public void getBeer() throws Exception {
        given(customerService.getCustomerById(uuid)).willReturn(customerDto);

        mockMvc.perform(get("/api/v1/customer/" + customerDto.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(customerDto.getId().toString())))
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    public void handlePost() throws Exception {

        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.saveNewCustomer(any(CustomerDto.class))).willReturn(customerDto);

        mockMvc.perform(post("/api/v1/customer/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","/api/v1/customer/"+customerDto.getId().toString()))
                .andExpect(jsonPath("$.id", is(uuid.toString())))
                .andExpect(jsonPath("$.name", is("John")));

    }

    @Test
    public void handleUpdate() throws Exception {

        CustomerDto customerDtoUpdatedDto =  CustomerDto.builder().id(uuid).name("Ken").build();

        String saveDtoJson = objectMapper.writeValueAsString(customerDtoUpdatedDto);

        given(customerService.updateCustomer(uuid, customerDtoUpdatedDto)).willReturn(customerDtoUpdatedDto);
        mockMvc.perform(put("/api/v1/customer/update/" + uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(saveDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(uuid.toString())))
                .andExpect(jsonPath("$.name", is("Ken")));
     }

     @Test
    public void handleDelete_succeed() throws Exception{
        given(customerService.getCustomerById(uuid)).willReturn(customerDto);
        willDoNothing().given(customerService).deleteById(uuid);
        mockMvc.perform(delete("/api/v1/customer/delete/" + uuid.toString()))
                .andExpect(status().isNoContent());

     }

     @Test
    public void handleDelete_fail() throws Exception{
        willThrow(new RuntimeException("Customer not found")).given(customerService).getCustomerById(uuid);
        mockMvc.perform(delete("/api/v1/customer/delete/"+ uuid.toString()))
                .andExpect(status().isBadRequest());
     }
}

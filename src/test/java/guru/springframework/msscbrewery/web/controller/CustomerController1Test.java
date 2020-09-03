package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;


public class CustomerController1Test {

    @Mock
    CustomerService customerService;

    CustomerController customerController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                  .setControllerAdvice(new MvcExceptionHandler())
                 .build();


    }

     @Test
     public void testhandlePost_error() throws Exception{
        CustomerDto customerDto = CustomerDto.builder()
                 .id(UUID.randomUUID())
                 .name("").build();
        String customerDtoJson = mapper.writeValueAsString(customerDto);
        when(customerService.saveNewCustomer(customerDto)).thenReturn(customerDto);
        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson)
                )
                .andExpect(status().isBadRequest());
        verify(customerService,times(0)).saveNewCustomer(any(CustomerDto.class));
     }

     @Test
    public void testhandlePost() throws Exception{
         CustomerDto customerDtoValid = CustomerDto.builder()
                 .id(UUID.randomUUID())
                 .name("John").build();
         String customerDtoJson = mapper.writeValueAsString(customerDtoValid);

         when(customerService.saveNewCustomer(customerDtoValid)).thenReturn(customerDtoValid);
         mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
              ).andExpect(status().isCreated());
         verify(customerService,times(1)).saveNewCustomer(any(CustomerDto.class));
     }

}
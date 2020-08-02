package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerV2Test {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int port;

    CustomerDto customerDto;
    @Before
    public void setUp(){
        customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Test
    public void handlePost(){
        testRestTemplate.postForObject("http://localhost:"+ port + "/api/v2/customer",
                customerDto, CustomerDto.class);

    }

}
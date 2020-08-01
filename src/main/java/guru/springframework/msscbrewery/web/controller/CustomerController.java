package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-04-21.
 */

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId")  UUID customerId){

        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDto> handlePost(@RequestBody CustomerDto customerDto){
          CustomerDto savedDto = customerService.saveNewCustomer(customerDto);
          HttpHeaders headers = new HttpHeaders();
          headers.add("Location", "/api/v1/customer/"+savedDto.getId().toString());
          return new ResponseEntity<>(savedDto,headers, HttpStatus.CREATED);
    }

    @PutMapping({"/update/{customerId}"})
    public ResponseEntity<CustomerDto> handleUpdate(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto){

        CustomerDto customerDtoUpdated= customerService.updateCustomer(customerId, customerDto);

        return new ResponseEntity<>(customerDtoUpdated,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public  ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("customerId") UUID customerId) {
        if (customerService.getCustomerById(customerId)!=null) {
            customerService.deleteById(customerId);
          return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else{
            throw new RuntimeException("Customer not found");
      }
    }
}

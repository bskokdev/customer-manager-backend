package com.skokcmd.customer;

import com.skokcmd.domain.request.CustomerRegistrationRequest;
import com.skokcmd.domain.response.CustomerRegistrationResponse;
import com.skokcmd.domain.response.GetCustomerResponse;
import com.skokcmd.domain.response.ResponseStatus;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  // POST - registers a new customer; see CustomerRegistrationRequest class
  @PostMapping
  public CustomerRegistrationResponse registerCustomer(
      @RequestBody CustomerRegistrationRequest newCustomerReq) {

    // this can be null - see CustomerService class
    Customer newCustomer = customerService.registerCustomer(newCustomerReq);
    if (newCustomer != null) {
      return new CustomerRegistrationResponse(
          ResponseStatus.SUCCESS, "Created a new customer", newCustomer);
    }

    return new CustomerRegistrationResponse(
        ResponseStatus.FAILED, "Cannot create a new customer - Fraudster or an invalid email!");
  }

  // GET all customers
  @GetMapping
  public List<Customer> getCustomers() {
    return this.customerService.findCustomers();
  }

  // GET customer by id
  @GetMapping(path = "/{customerId}")
  public GetCustomerResponse getCustomerById(@PathVariable UUID customerId) {
    Customer foundCustomer = this.customerService.findCustomerById(customerId);

    if (foundCustomer != null) {
      return new GetCustomerResponse(ResponseStatus.SUCCESS, "Found customer!", foundCustomer);
    }
    return new GetCustomerResponse(ResponseStatus.FAILED, "No user found!");
  }
}

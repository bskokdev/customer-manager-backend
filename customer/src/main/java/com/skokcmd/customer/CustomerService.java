package com.skokcmd.customer;

import com.skokcmd.domain.request.CustomerRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerValidationService validationService;

  // if customer is creatable and valid he's registered
  public Map<Customer, Boolean> registerCustomer(CustomerRegistrationRequest req) {
    Customer newCustomer =
        Customer.builder()
            .firstName(req.firstName())
            .lastName(req.lastName())
            .email(req.email())
            .build();
    boolean isCreatable = false;
    boolean isValidCustomer = validationService.isCustomerValid(newCustomer.getEmail());
    if (isValidCustomer) {
      isCreatable = true;
      try {
        if (validationService.isFraudster(newCustomer.getEmail())) {
          isCreatable = false;
        }
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }
    if (isValidCustomer && isCreatable) customerRepository.save(newCustomer);
    return Collections.singletonMap(newCustomer, isCreatable);
  }

  // finds and returns all customers
  public List<Customer> findCustomers() {
    return customerRepository.findAll();
  }

  // finds customer by id
  public Customer findCustomerById(UUID id) {
    Optional<Customer> customerRes = customerRepository.findById(id);
    return customerRes.get();
  }
}

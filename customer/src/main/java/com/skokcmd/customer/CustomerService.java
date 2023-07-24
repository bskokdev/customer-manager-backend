package com.skokcmd.customer;

import com.skokcmd.domain.request.CustomerRegistrationRequest;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

/** Service for customer-related operations such as registration, finding customers, etc. */
@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerValidationService validationService;

  /**
   * Registers a new customer if the customer is not a fraudster and the email is valid
   *
   * @param req customer registration request
   * @return the newly created customer or null if the customer is a fraudster or email is invalid
   */
  public Customer registerCustomer(CustomerRegistrationRequest req) {
    Customer newCustomer =
        Customer.builder()
            .firstName(req.firstName())
            .lastName(req.lastName())
            .email(req.email())
            .build();

    if (canRegisterCustomer(newCustomer)) {
      return customerRepository.save(newCustomer);
    }
    return null;
  }

  private boolean canRegisterCustomer(Customer customer) {
    boolean isValidCustomer = validationService.isCustomerValid(customer.getEmail());
    if (isValidCustomer) {
      try {
        if (validationService.isFraudster(customer.getEmail())) {
          return false;
        }
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
    }
    return isValidCustomer;
  }

  // finds and returns all customers
  public List<Customer> findCustomers() {
    return customerRepository.findAll();
  }

  // finds customer by id
  public Customer findCustomerById(UUID id) {
    Optional<Customer> customerRes = customerRepository.findById(id);
    return customerRes.orElseGet(customerRes::get);
  }
}

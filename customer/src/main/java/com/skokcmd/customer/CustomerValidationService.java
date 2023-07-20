package com.skokcmd.customer;

import com.skokcmd.domain.response.FraudCheckResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerValidationService {

  private static final String FRAUD_CHECK_SERVICE_URL = "http://localhost:8082/api/v1/fraud-check/";

  private final CustomerRepository customerRepository;
  private final RestTemplate restTemplate;

  /**
   * Checks if the string is an email
   *
   * @param s provided String
   * @return if it's an email
   */
  public boolean isEmail(String s) {
    return s.contains("@");
  }

  /**
   * Checks whether the email is already taken
   *
   * @param email provided email
   * @return whether the email is taken
   */
  public boolean isEmailTaken(String email) {
    return customerRepository.findAll().stream().map(Customer::getEmail).toList().contains(email);
  }

  /**
   * Checks if the customer is a fraudster or not
   *
   * @param customerEmail customer's email
   * @return whether a customer is a fraudster
   */
  public boolean isFraudster(String customerEmail) throws JSONException {
    FraudCheckResponse res =
        restTemplate.getForObject(
            FRAUD_CHECK_SERVICE_URL + "{customerEmail}", FraudCheckResponse.class, customerEmail);
    return res != null && res.isFraudster();
  }

  /**
   * Checks if the customer email input is valid
   *
   * @param email customer's email
   * @return whether the customer input is valid
   */
  public boolean isCustomerValid(String email) {
    return !isEmailTaken(email) && isEmail(email);
  }
}

package com.skokcmd.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service for fraudster-related operations */
@Service
@RequiredArgsConstructor
public class FraudsterService {

  private final FraudstersRepository fraudstersRepository;

  /**
   * Checks if the customer is a fraudster or not
   *
   * @param customerEmail customer's email
   * @return whether a customer is a fraudster
   */
  public boolean isFraudster(String customerEmail) {
    return fraudstersRepository.findAll().stream()
        .anyMatch(fraudster -> fraudster.getCustomerEmail().equals(customerEmail));
  }

  /**
   * Marks a customer's email as a fraudster
   *
   * @param customerEmail customer's email
   */
  public void markAsFraudster(String customerEmail) {
    Fraudster newFraudster = Fraudster.builder().customerEmail(customerEmail).build();
    fraudstersRepository.save(newFraudster);
  }
}

package com.skokcmd.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudstersService {

  private final FraudstersRepository fraudstersRepository;

  // returns if email is present in fraudsters' table
  public boolean isFraudster(String customerEmail) {
    return fraudstersRepository.findAll().stream()
        .anyMatch(fraudster -> fraudster.getCustomerEmail().equals(customerEmail));
  }

  // marks email as fraudster
  public void markAsFraudster(String customerEmail) {
    Fraudster newFraudster = Fraudster.builder().customerEmail(customerEmail).build();
    fraudstersRepository.save(newFraudster);
  }
}

package com.skokcmd.fraudCheck;

import com.skokcmd.fraud.Fraud;
import com.skokcmd.fraud.FraudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudCheckService {
  private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
  private final FraudRepository fraudRepository;

  // sees if customer's email is in the fraudsters list
  public boolean isCustomerFraudster(String customerEmail) {
    boolean isFraudster =
        fraudRepository.findAll().stream()
            .map(Fraud::getCustomerEmail)
            .collect(Collectors.toList())
            .contains(customerEmail);
    // creates fraud check
    FraudCheckHistory fraudCheck =
        FraudCheckHistory.builder()
            .customerEmail(customerEmail)
            .passed(!isFraudster)
            .triggeredAt(LocalDateTime.now())
            .build();
    fraudCheckHistoryRepository.saveAndFlush(fraudCheck);
    return isFraudster;
  }
}

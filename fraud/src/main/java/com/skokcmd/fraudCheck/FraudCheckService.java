package com.skokcmd.fraudCheck;

import com.skokcmd.fraud.FraudsterService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service for fraud checks */
@Service
@RequiredArgsConstructor
public class FraudCheckService {

  private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
  private final FraudsterService fraudstersService;

  /**
   * Looks up the customer in the fraudster database and creates a record of the check
   *
   * @param customerEmail customer's email
   * @return whether the customer is a fraudster
   */
  public boolean isCustomerFraudsterAndMakeRecord(String customerEmail) {
    boolean isFraudster = fraudstersService.isFraudster(customerEmail);

    // creates a fraud check
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

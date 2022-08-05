package com.skokcmd.fraudCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/fraud-check")
@RequiredArgsConstructor
public class FraudCheckController {

  private final FraudCheckService fraudCheckService;

  @GetMapping(path = "/{customerEmail}")
  public FraudCheckResponse isFraudster(@PathVariable String customerEmail) {
    return new FraudCheckResponse(fraudCheckService.isCustomerFraudster(customerEmail));
  }
}

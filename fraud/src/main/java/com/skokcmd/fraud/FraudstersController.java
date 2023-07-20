package com.skokcmd.fraud;

import com.skokcmd.domain.GenericResponse;
import com.skokcmd.domain.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/fraudsters")
@RequiredArgsConstructor
public class FraudstersController {

  private final FraudstersService fraudstersService;

  // marks email as a fraudster email -> a customer account can't be created
  @PostMapping
  public GenericResponse markAsFraudster(@RequestParam String userEmail) {
    fraudstersService.markAsFraudster(userEmail);
    return new GenericResponse(ResponseStatus.SUCCESS, "marked");
  }
}

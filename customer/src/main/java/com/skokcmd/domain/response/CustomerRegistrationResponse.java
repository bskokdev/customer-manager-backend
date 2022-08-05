package com.skokcmd.domain.response;

import com.skokcmd.customer.Customer;
import lombok.Data;

@Data
public class CustomerRegistrationResponse extends GenericResponse {
  private Customer customer;

  public CustomerRegistrationResponse(ResponseStatus status, String mess, Customer customer) {
    super(status, mess);
    this.customer = customer;
  }

  public CustomerRegistrationResponse(ResponseStatus status, String mess) {
    super(status, mess);
  }
}

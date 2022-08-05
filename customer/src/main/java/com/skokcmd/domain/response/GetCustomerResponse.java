package com.skokcmd.domain.response;

import com.skokcmd.customer.Customer;
import lombok.Data;

@Data
public class GetCustomerResponse extends GenericResponse {

  private Customer customer;

  public GetCustomerResponse(ResponseStatus status, String mess) {
    super(status, mess);
  }

  public GetCustomerResponse(ResponseStatus status, String message, Customer customer) {
    super(status, message);
    this.customer = customer;
  }
}

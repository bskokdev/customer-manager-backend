package com.skokcmd.domain.request;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {}

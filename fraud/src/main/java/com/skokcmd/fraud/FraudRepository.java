package com.skokcmd.fraud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FraudRepository extends JpaRepository<Fraud, UUID> {}

package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.dto.PurchaseTransactionResponse;
import com.polovyi.ivan.entity.CustomerEntity;
import com.polovyi.ivan.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository) {

    public List<CustomerResponse> getAllCustomers() {
        log.info("Getting all customers...");
        return customerRepository.findAll().stream().map(CustomerResponse::valueOf).collect(Collectors.toList());
    }

    public CustomerResponse getCustomersById(String customerId) {
        log.info("Getting customer by id...");
        return customerRepository.findById(customerId).map(CustomerResponse::valueOf).orElse(null);

    }

    public List<PurchaseTransactionResponse> getAllCustomerPurchaseTransactions(String customerId) {
        log.info("Getting all customer purchase transactions...");
        return customerRepository.findById(customerId)
                .map(CustomerEntity::getPurchaseTransactions)
                .orElseGet(ArrayList::new)
                .stream()
                .map(PurchaseTransactionResponse::valueOf)
                .collect(Collectors.toList());
    }

    public PurchaseTransactionResponse getCustomerPurchaseTransactionsById(String customerId,
            String purchaseTransactionId) {
        log.info("Getting customer purchase transaction by id...");
        return customerRepository.findById(customerId)
                .map(CustomerEntity::getPurchaseTransactions)
                .orElseGet(ArrayList::new)
                .stream()
                .filter(purchaseTransaction -> purchaseTransactionId.equals(purchaseTransaction.getId()))
                .map(PurchaseTransactionResponse::valueOf)
                .findFirst()
                .orElse(null);
    }

    public List<CustomerResponse> getAllCustomersWithFilters(String fullName, String phoneNumber,
            LocalDate createdAt, String paymentType) {
          log.info("Getting all customers with filters fullName {}...", fullName, phoneNumber, createdAt, paymentType );
        return customerRepository.findCustomersWithFilters(fullName, phoneNumber, createdAt, paymentType)
                .stream()
                .map(CustomerResponse::valueOf)
                .collect(Collectors.toList());
    }
}

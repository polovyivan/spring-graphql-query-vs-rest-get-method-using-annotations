package com.polovyi.ivan.controller;

import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.dto.PurchaseTransactionResponse;
import com.polovyi.ivan.service.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public record CustomerRESTController(CustomerService customerService) {

    @GetMapping(path = "/v1/customers-all")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/v1/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomersWithFilters(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdAt,
            @RequestParam(required = false) String paymentType) {
        return customerService.getAllCustomersWithFilters(fullName, phoneNumber, createdAt, paymentType);
    }

    @GetMapping(path = "/v1/customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomerById(@PathVariable String customerId) {
        return customerService.getCustomersById(customerId);
    }

    @GetMapping(path = "/v1/customers/{customerId}/purchase-transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseTransactionResponse> getAllCustomerPurchaseTransactions(@PathVariable String customerId) {
        return customerService.getAllCustomerPurchaseTransactions(customerId);
    }

    @GetMapping(path = "/v1/customers/{customerId}/purchase-transactions/{purchaseTransactionId}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseTransactionResponse getCustomerPurchaseTransactionById(@PathVariable String customerId,
            @PathVariable String purchaseTransactionId) {
        return customerService.getCustomerPurchaseTransactionsById(customerId, purchaseTransactionId);
    }

}

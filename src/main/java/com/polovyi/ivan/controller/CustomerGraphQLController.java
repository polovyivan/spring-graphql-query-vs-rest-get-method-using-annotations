package com.polovyi.ivan.controller;

import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.dto.PurchaseTransactionResponse;
import com.polovyi.ivan.service.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
public record CustomerGraphQLController(CustomerService customerService) {

    @QueryMapping
    public String hello() {
        return "Hello, world!";
    }

    @QueryMapping(name = "customers")
    public List<CustomerResponse> getAllCustomersWithFilters(
            @Argument String fullName,
            @Argument String phoneNumber,
            @Argument @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdAt,
            @Argument String paymentType) {
        return customerService.getAllCustomersWithFilters(fullName, phoneNumber, createdAt, paymentType);
    }

    @QueryMapping(name = "customer")
    public CustomerResponse getCustomerById(@Argument @NotNull String customerId) {
        return customerService.getCustomersById(customerId);
    }


    @QueryMapping(name = "customerTransactions")
    public List<PurchaseTransactionResponse> getAllCustomerPurchaseTransactions(@Argument @NotNull String customerId) {
        return customerService.getAllCustomerPurchaseTransactions(customerId);
    }

    @QueryMapping(name = "customerTransaction")
    public PurchaseTransactionResponse getCustomerById(@Argument @NotNull String customerId,
                                                       @Argument @NotNull String purchaseTransactionId) {
        return customerService.getCustomerPurchaseTransactionsById(customerId, purchaseTransactionId);
    }

}

package com.spring.kafka.Enpoint.dto;

import lombok.Data;

import jakarta.validation.constraints.*;
@Data
public class senddto {

    @NotBlank private String userKey;
    @NotBlank private String companyName;
    @NotBlank private String companyType;
    private String companyDescription;
    @NotBlank private String taxId;
    @NotBlank private String locale;
    @NotBlank private String price;
    @NotBlank private String paymentGroup;
    @NotBlank private String cardHolderName;
    @NotBlank private String cardNumber;
    @NotBlank private String expireMonth;
    @NotBlank private String expireYear;
    @NotBlank private String cvc;
    @NotBlank private String buyerIp;
    @NotBlank private String buyerName;
    @NotBlank private String buyerSurname;
    @NotBlank private String buyerGsmNumber;
    @NotBlank private String buyerEmail;
    @NotBlank private String buyerTckn;
    @NotBlank private String buyerRegistrationAddress;
    @NotBlank private String buyerCity;
    @NotBlank private String buyerCountry;
    @NotBlank private String buyerZipCode;
    @NotBlank private String itemName;
    private String itemCategory1;
    private String itemDescription;
    @NotBlank private String itemType;
    @NotBlank private String itemPrice;
}
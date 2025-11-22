package com.spring.kafka.data.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// ENTITIYNIN ESKI HALI ASSAGIDA.
// builderi kaldirdim cunku jdbc ile beraber kullanilinca patliyor. oyuzden altta default degerleri yazan yer var

@Entity
@Table(name = "data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true) // bunu yazmayinca hata veriyor.
public class Data {

    // @Column(name = "user_key") yazmamiza gerek yok otomatik yapiyor kendisi

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userKey; // bunu user_key gibi veritabanina kaydediyor

    private String ip;

    @Column(name = "blacklist")
    private Boolean blacklist = false;

    private String useragent;

    private String headers;

    private String requesturl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String companyName;

    private String companyType;

    private String companyDescription;

    private String taxId;

    private String locale;

    private String price;

    private String paymentGroup;

    private String cardHolderName;

    private String cardNumber;

    private String expireMonth;

    private String expireYear;
    private String cvc;

    private String buyerName;

    private String buyerSurname;

    private String buyerGsmNumber;

    private String buyerEmail;

    private String buyerTckn;

    private String buyerRegistrationAddress;

    private String buyerIp;

    private String buyerCity;

    private String buyerCountry;

    private String buyerZipCode;

    private String itemName;

    private String itemCategory1;

    private String itemDescription;

    private String itemType;

    private String itemPrice;



    @PrePersist // BUILDER KULLANAMADIGIM ICIN BUNU YAZDIM jdbc ile beraber kullanilmiyor
    protected void Builder() {

        if (createdAt == null) createdAt = LocalDateTime.now();
        if (userKey == null) userKey = "unknown";
        if (useragent == null) useragent = "unknown";
        if (requesturl == null) requesturl = "unknown";
        if (headers == null) headers = "unknown";

    }
}




//@Entity
//@Table(name = "data")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder(toBuilder = true) // jdbctemplate ile builder cok fazla hata veriyor oyuzden poostresql ile calistirmak onemli
//public class Data {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // id ve olmasi zozrunlu
//    private Long id;
//
//    @Builder.Default
//    @Column(name = "userKey")
//    private String userKey = "unknown";
//
//    @Column(name = "ip")
//    private String ip;
//
//    @Builder.Default
//    @Column(name = "useragent")
//    private String useragent = "unknown";
//
//    @Builder.Default
//    @Column(name = "headers")
//    private String headers = "unknown";
//
//    @Builder.Default
//    @Column(name = "requesturl")
//    private String requesturl = "unknown";
//
//    @Builder.Default
//    @Column(name = "createdAt")
//    private LocalDateTime createdAt = LocalDateTime.parse(Instant.now().toString()); // instant utc saati vertdigi icin kullanmakt daha iyi serverin local saatine gore veriyor Datetime.now
//
//    @Column(name = "companyName")
//    private String companyName;
//
//    @Column(name = "companyType")
//    private String companyType;
//
//    @Column(name = "companyDescription")
//    private String companyDescription;
//
//    @Column(name = "taxId")
//    private String taxId;
//
//    @Column(name = "locale")
//    private String locale;
//
//    @Column(name = "price")
//    private String price;
//
//    @Column(name = "paymentGroup")
//    private String paymentGroup;
//
//    @Column(name = "cardHolderName")
//    private String cardHolderName;
//
//    @Column(name = "cardNumber")
//    private String cardNumber;
//
//    @Column(name = "expireMonth")
//    private String expireMonth;
//
//    @Column(name = "expireYear")
//    private String expireYear;
//
//    @Column(name = "cvc")
//    private String cvc;
//
//    @Column(name = "buyerName")
//    private String buyerName;
//
//    @Column(name = "buyerSurname")
//    private String buyerSurname;
//
//    @Column(name = "buyerGsmNumber")
//    private String buyerGsmNumber;
//
//    @Column(name = "buyerEmail")
//    private String buyerEmail;
//
//    @Column(name = "buyerTckn")
//    private String buyerTckn;
//
//    @Column(name = "buyerAddress")
//    private String buyerRegistrationAddress;
//
//    @Column(name = "buyerIp")
//    private String buyerIp;
//
//    @Column(name = "buyerCity")
//    private String buyerCity;
//
//    @Column(name = "buyerCountry")
//    private String buyerCountry;
//
//    @Column(name = "buyerZipCode")
//    private String buyerZipCode;
//
//    @Column(name = "itemName")
//    private String itemName;
//
//    @Column(name = "itemCategory")
//    private String itemCategory1;
//
//    @Column(name = "itemDescription")
//    private String itemDescription;
//
//    @Column(name = "itemType")
//    private String itemType;
//
//    @Column(name = "itemPrice")
//    private String itemPrice;
//}
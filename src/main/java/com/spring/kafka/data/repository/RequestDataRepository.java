package com.spring.kafka.data.repository;

import com.spring.kafka.data.Entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestDataRepository extends JpaRepository<Data, Long> {

    Optional<Data> findByIp(String ip);

    Optional<Data> findByRequesturl(String requesturl);

    boolean existsByIpAndBlacklistTrue(String ip);

    Optional<String> findReasonByIp(String ip);


// data.setBlacklist(true);





//    @Modifying
//    @Transactional // insert update delete icin zorunlu
//    @Query(value = "INSERT INTO Requestdata (ip, headers, lastseen, endpointusagecount, lastrequesturl, lastrequestbody )" +
//            "VALUES (:#{data.ip},  :#{data.headers} , :#{data.lastseen}, :#{#data.endpointusagecount} , :#{#data.requesturl} ,  :#{#data.requestbody})", nativeQuery = true)
//
//    // :#{#data.requesturl}
//    // :#{#requestdataturundegelenparametre.requesturl} // requestdata turunde gelen parametreyi data olarak kaydettik. :#{#data.requesturl} diye yazmak daha kolay ? ile yazmaktansa
//
//    void insertrequestdata(@Param("data") Requestdata data);
}

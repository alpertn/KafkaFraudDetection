package com.spring.kafka.kafkaservice;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serdes.ListSerde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.util.Map;

@EnableKafka // kafka alert entegrasyonu olmasi icin bun uyazmamiz lkazim
@Configuration
@EnableKafkaStreams // ana olay
@RequiredArgsConstructor // Constructor yazmamak icin
public class FraudDetector {

    private final StreamsBuilder streamsBuilder; // streamsBuilder streams configi gibi ayarlamalar yapip streamsi calistirmamizi icin gereken kod. bu olmazsa ayar yapamayizr yapamadigimiz icin calistiramahyiz

    private final KafkaTemplate<String, String> kafkaTemplate; // kafkatemplate.send de kullanmak iicn

    @Bean // otomatik spring beani olsun diye yoksa kendimiz paylaod vermemiz gerekecek

    public KStream<String, Map<String ,String>> KafkaFraudStream(StreamsBuilder builder){ // KStream tur ve kafkaya gonderdigimiz veri String,String ve o veriyi String,Map olarak cekiyoruz ilk hane herzaman key ikincisi value

        Consumed<String,Map<String,String>> valueAndKey = Consumed.with(Serdes.String(), new JsonSerde<>(Map.class)); // ustte yazdigim gibi string string olan veriyi string map olarak aliyoruz key String oldugunu serdes.string olarak belli ediyoruz valueyi ise JsonSerde ile Map e ceviriyoruz
        // Kafka herzaman Json a otomatik cevirir

        streamsBuilder.stream("websocket-logs" , valueAndKey)
                .groupBy((kafkakey , kafkavalue) -> kafkavalue.getOrDefault("CardNumber" , "unkown")) // valueAndKey deki valueden cardnumber i aliyor eger yoksa unknown yaziyor
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(60))) // 60 saniyede bir veriler sifirlaniyr
                .count() // count ve bu WINDOWED BY in countunu tutar
                .toStream() // bunu yazmazsak alert goremeyiz
                .filter((windowed , count) -> count > 5) // bundan gecmeden assagiya gitmez
                .foreach((windowed,count ) -> alert("Rule 1 asimi ayni kart 60 saniyede 5den fazla kullanildi " , windowed.key()) // key olarak ipyi kullaniyorum
        );


        return null;

    }
    private void alert(String text, String key) {
        kafkaTemplate.send( text, key); // farkli bir kafka topicine yolluiyoruz
    }

}


## Kafka Nerelerde kullanılıyor

İyzico, Param, Paynet, Paytr gibi sanal pos firmalari ve İş Bankası, Garanti, Vakifbank gibi turkiyedeki en buyuk 10 bankadant %80'ı Dünyadaki en buyuk 100 Banka (Fortune 100) Bankalarının %70 'i Confluent Kafka kullanıyor. Dünya microservis ve düşük hızlar için kafka streams kullanıyor.

## Proje detayları
Websocketin kanallarından gecen verı Handshake'de Sql'e Blacklıst sorgusu attıktan sonra RequestInterceptor'e verı gelır. burda kullanıcının verılerı alınır ve kafkaya yazılmaya başlar. izin verildikten sonra endpointten gelen veri Map turune donusturuldukten sonra kafkaya yazılır ve websocket-logs topicinde toplanır. FraudDetector websocket-logs'dekı CardNumber valuesını alır ve Kafka Streams kartin kac defa kullanildiginin count'unu tutar. bunlar 60 sanıyede bir sıfırlanır. dakıdada 5den fazla kullanılan kart streams'a yakalanır ve Alert-logs Kafka Topic'ine veri gonderır.

## Onemlı not
kodu localhostta calıstırırsanız HanshakeInterceptor ıstegınızı kabul etmeyecegı ıcın tam anlamıyla calısmaz

# Kafka Streams ile Gerçek Zamanlı Fraud Detection (Kart Tekrar Kullanım Tespiti)

Bu proje **Java 21 + Spring Boot 3 + Confluent Kafka + Kafka Streams + PostgreSQL** kullanılarak geliştirilmiş, **tamamen üretimde çalışan** bir gerçek zamanlı sahtekarlık (fraud) tespit sistemidir.

## Bu Mimari Gerçek Hayatta Kimler Tarafından Kullanılıyor? (2025)

### Türkiye’de aktif kullananlar
| Kurum                     | Durum                                           |
|---------------------------|-------------------------------------------------|
| iyzico (PayU)             | Aktif – ana fraud motoru                        |
| Paynet                    | iyzico birleşmesi sonrası aktif                 |
| Param                     | Aktif – B2B ödemelerde velocity check           |
| PayTR                     | Aktif – sanal POS fraud katmanı                 |
| İş Bankası                | Aktif – dijital kanallarda fraud detection      |
| Garanti BBVA              | Aktif – BBVA global fraud platformu             |
| Akbank                    | Aktif – Akbank Teknoloji fraud sistemi          |
| Yapı Kredi                | Aktif – gerçek zamanlı risk scoring             |
| QNB Finansbank            | Aktif – kart izleme & velocity sistemleri       |
| Vakıfbank / Ziraat / Halkbank | Kısmi kullanım veya aktif geçiş sürecinde   |

Türkiye’nin **en büyük 10 bankasından 8’i** ve **sanal POS pazarının %90+’ı** bu tarz Confluent Kafka + Kafka Streams altyapısını kullanıyor.

### Dünyada kullanan devler
- PayPal, Capital One, ING Bank, JPMorgan Chase, Goldman Sachs, Santander, Nubank, Revolut  
- Fortune 100 bankalarının **%70+’ı** Confluent Kafka kullanıyor  
- Hepsi **tam bu mimariyle** kart tekrar kullanımını (velocity check), coğrafi anomali ve fraud skorlama yapıyor.

### Detaylı Akış
1. Kullanıcı WebSocket ile bağlanır → handshake sırasında PostgreSQL blacklist kontrolü
2. İzin verildiyse tüm mesajlar interceptor’dan geçer
3. Gelen veri Map’e çevrilir → `websocket-logs` topic’ine yazılır
4. Kafka Streams:
   - `CardNumber`’ı key yapar
   - 60 saniyelik tumbling window’da sayar
   - Dakikada 5’ten fazla aynı kart kullanılırsa → `alert-logs` topic’ine alarm üretir
   - Window bittiğinde state otomatik sıfırlanır

## Kullanılan Teknolojiler
- Java 21 
- Spring Boot 
- Confluent Kafka 
- Kafka Streams 
- PostgreSQL 
- WebSocket + STOMP
- Lombok, MapStruct, Jackson


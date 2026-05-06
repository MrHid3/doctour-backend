                                                                                   ███       ████                                                            
                  ███                                                                                   ███         ████                                                           
                  ███             █████████████████████████████████████████████████████████████        ███           ██████████████    ███                                         
                  ███                                            ███                        ████     ████                               █                                          
                  ███                                            ███                        ████    ████                                                                           
         ████████ ███        ████████          █████████         ███          ████████      ████  █████   ██  ████████  ████████       ███     ███ █████████                       
       ████     █████      ███      ███       ███      █         ███        ███     ████    ██████████    ████     ██████     ███      ███     █████     ████                      
       ██         ███     ███        ███     ███                 ███       ███       ███    ██████        ███       ████       ███     ███     ████       ███                      
      ███         ███    ███          ███   ███                  ███       ██         ███   ████          ███        ██        ███     ███     ███         ██                      
      ███         ███    ███          ███   ███                  ███      ███████████████   ████          ███        ██        ███     ███     ███         ██                      
      ███         ███    ███          ███   ███                  ███      ███               ████          ███        ██        ███     ███     ███         ██                      
      ███         ███     ███        ███     ███                 ███       ███              ████          ███        ██        ███     ███     ███         ██                      
       ███       ████      ███      ███      ████                ███        ███        █    ████          ███        ██        ███     ███     ███         ██
         ████████ ███       ██████████         █████████         ███          ██████████     ███          ███        ██        ███     ███     ███         ██
---

Witaj w README powalająco szybkiego, dynamicznie skalującego i modułowo dostosowanego do potrzeb każdego klienta API *doktermin* (nazwa wymyślona z pomocą [namenssuchmaschine](https://github.com/HexHyperion/namenssuchmaschine)). Poniżej jest napisana na kolanie dokumentacja na niego. Nawet nie mam nadzieji że nie będzie pytań.

---

## Rzeczy do zrobienia

- [x] logowanie/rejestracja/cookie/whatever
- [x] doktor ustawia lokalizacje/grafik/type shit
- [x] użytkownik może się do niego umówić
- [x] anulowanie itd, system powiadomień
- [x] szukanie lekarzy po lokalizacji
- [x] feature flagi w envie

# Jak to coś odpalić

~~potrzeba serwera postgresa, z którym łączysz się w .env, z zainstalowanymi pluginami plpgsql (nie mam pojęcia po co go instalowałem) oraz postgis (do koordynat ggeograficznych)~~

~~trzeba również podpiąć jakieś API do maili (np. mailtrapa) i również je wpisać w .envie~~

~~frontend_url jest póki co uselss bo nie ma frontendu~~

~~potem wystaczy wstrzyknąć te envy i odpalić jave~~

~~sprawdzona jest java temurin-22~~

~~intelij runuje jakieś~~
```
/usr/lib/jvm/java-22-temurin/bin/java -XX:TieredStopAtLevel=1 -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true -Dmanagement.endpoints.jmx.exposure.include=* -javaagent:/home/gabriel/.local/share/JetBrains/Toolbox/apps/intellij-idea/lib/idea_rt.jar=35059 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /home/gabriel/IdeaProjects/doctour-backend/target/classes:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-restclient/4.0.3/spring-boot-starter-restclient-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter/4.0.3/spring-boot-starter-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-logging/4.0.3/spring-boot-starter-logging-4.0.3.jar:/home/gabriel/.m2/repository/ch/qos/logback/logback-classic/1.5.32/logback-classic-1.5.32.jar:/home/gabriel/.m2/repository/ch/qos/logback/logback-core/1.5.32/logback-core-1.5.32.jar:/home/gabriel/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.25.3/log4j-to-slf4j-2.25.3.jar:/home/gabriel/.m2/repository/org/apache/logging/log4j/log4j-api/2.25.3/log4j-api-2.25.3.jar:/home/gabriel/.m2/repository/org/slf4j/jul-to-slf4j/2.0.17/jul-to-slf4j-2.0.17.jar:/home/gabriel/.m2/repository/jakarta/annotation/jakarta.annotation-api/3.0.0/jakarta.annotation-api-3.0.0.jar:/home/gabriel/.m2/repository/org/yaml/snakeyaml/2.5/snakeyaml-2.5.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-jackson/4.0.3/spring-boot-starter-jackson-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-jackson/4.0.3/spring-boot-jackson-4.0.3.jar:/home/gabriel/.m2/repository/tools/jackson/core/jackson-databind/3.0.4/jackson-databind-3.0.4.jar:/home/gabriel/.m2/repository/tools/jackson/core/jackson-core/3.0.4/jackson-core-3.0.4.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-restclient/4.0.3/spring-boot-restclient-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-http-client/4.0.3/spring-boot-http-client-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-security/4.0.3/spring-boot-starter-security-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-security/4.0.3/spring-boot-security-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/security/spring-security-config/7.0.3/spring-security-config-7.0.3.jar:/home/gabriel/.m2/repository/org/springframework/security/spring-security-core/7.0.3/spring-security-core-7.0.3.jar:/home/gabriel/.m2/repository/org/springframework/security/spring-security-crypto/7.0.3/spring-security-crypto-7.0.3.jar:/home/gabriel/.m2/repository/org/springframework/security/spring-security-web/7.0.3/spring-security-web-7.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-expression/7.0.5/spring-expression-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/spring-aop/7.0.5/spring-aop-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/spring-beans/7.0.5/spring-beans-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-webmvc/4.0.3/spring-boot-starter-webmvc-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/4.0.3/spring-boot-starter-tomcat-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat-runtime/4.0.3/spring-boot-starter-tomcat-runtime-4.0.3.jar:/home/gabriel/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/11.0.18/tomcat-embed-core-11.0.18.jar:/home/gabriel/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/11.0.18/tomcat-embed-websocket-11.0.18.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-tomcat/4.0.3/spring-boot-tomcat-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-http-converter/4.0.3/spring-boot-http-converter-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-web/7.0.5/spring-web-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-webmvc/4.0.3/spring-boot-webmvc-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-servlet/4.0.3/spring-boot-servlet-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-webmvc/7.0.5/spring-webmvc-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-configuration-processor/4.0.3/spring-boot-configuration-processor-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-thymeleaf/4.0.3/spring-boot-starter-thymeleaf-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-thymeleaf/4.0.3/spring-boot-thymeleaf-4.0.3.jar:/home/gabriel/.m2/repository/org/thymeleaf/thymeleaf-spring6/3.1.3.RELEASE/thymeleaf-spring6-3.1.3.RELEASE.jar:/home/gabriel/.m2/repository/org/thymeleaf/thymeleaf/3.1.3.RELEASE/thymeleaf-3.1.3.RELEASE.jar:/home/gabriel/.m2/repository/org/attoparser/attoparser/2.0.7.RELEASE/attoparser-2.0.7.RELEASE.jar:/home/gabriel/.m2/repository/org/unbescape/unbescape/1.1.6.RELEASE/unbescape-1.1.6.RELEASE.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-mail/4.0.3/spring-boot-starter-mail-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-mail/4.0.3/spring-boot-mail-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-context-support/7.0.5/spring-context-support-7.0.5.jar:/home/gabriel/.m2/repository/jakarta/mail/jakarta.mail-api/2.1.5/jakarta.mail-api-2.1.5.jar:/home/gabriel/.m2/repository/org/eclipse/angus/angus-mail/2.0.5/angus-mail-2.0.5.jar:/home/gabriel/.m2/repository/org/eclipse/angus/angus-activation/2.0.3/angus-activation-2.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-devtools/4.0.3/spring-boot-devtools-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot/4.0.3/spring-boot-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-context/7.0.5/spring-context-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/4.0.3/spring-boot-autoconfigure-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-validation/4.0.3/spring-boot-starter-validation-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-validation/4.0.3/spring-boot-validation-4.0.3.jar:/home/gabriel/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/11.0.18/tomcat-embed-el-11.0.18.jar:/home/gabriel/.m2/repository/org/hibernate/validator/hibernate-validator/9.0.1.Final/hibernate-validator-9.0.1.Final.jar:/home/gabriel/.m2/repository/jakarta/validation/jakarta.validation-api/3.1.1/jakarta.validation-api-3.1.1.jar:/home/gabriel/.m2/repository/org/postgresql/postgresql/42.7.10/postgresql-42.7.10.jar:/home/gabriel/.m2/repository/org/checkerframework/checker-qual/3.52.0/checker-qual-3.52.0.jar:/home/gabriel/.m2/repository/org/hibernate/orm/hibernate-spatial/7.2.4.Final/hibernate-spatial-7.2.4.Final.jar:/home/gabriel/.m2/repository/org/hibernate/orm/hibernate-core/7.2.4.Final/hibernate-core-7.2.4.Final.jar:/home/gabriel/.m2/repository/jakarta/persistence/jakarta.persistence-api/3.2.0/jakarta.persistence-api-3.2.0.jar:/home/gabriel/.m2/repository/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.jar:/home/gabriel/.m2/repository/org/hibernate/models/hibernate-models/1.0.1/hibernate-models-1.0.1.jar:/home/gabriel/.m2/repository/com/fasterxml/classmate/1.7.3/classmate-1.7.3.jar:/home/gabriel/.m2/repository/net/bytebuddy/byte-buddy/1.17.8/byte-buddy-1.17.8.jar:/home/gabriel/.m2/repository/org/glassfish/jaxb/jaxb-runtime/4.0.6/jaxb-runtime-4.0.6.jar:/home/gabriel/.m2/repository/org/glassfish/jaxb/jaxb-core/4.0.6/jaxb-core-4.0.6.jar:/home/gabriel/.m2/repository/org/glassfish/jaxb/txw2/4.0.6/txw2-4.0.6.jar:/home/gabriel/.m2/repository/com/sun/istack/istack-commons-runtime/4.1.2/istack-commons-runtime-4.1.2.jar:/home/gabriel/.m2/repository/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar:/home/gabriel/.m2/repository/org/antlr/antlr4-runtime/4.13.2/antlr4-runtime-4.13.2.jar:/home/gabriel/.m2/repository/org/geolatte/geolatte-geom/1.10/geolatte-geom-1.10.jar:/home/gabriel/.m2/repository/org/locationtech/jts/jts-core/1.19.0/jts-core-1.19.0.jar:/home/gabriel/.m2/repository/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.jar:/home/gabriel/.m2/repository/org/jboss/logging/jboss-logging/3.6.2.Final/jboss-logging-3.6.2.Final.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-web-server/4.0.3/spring-boot-web-server-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-data-jpa/4.0.3/spring-boot-starter-data-jpa-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-jdbc/4.0.3/spring-boot-starter-jdbc-4.0.3.jar:/home/gabriel/.m2/repository/com/zaxxer/HikariCP/7.0.2/HikariCP-7.0.2.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-data-jpa/4.0.3/spring-boot-data-jpa-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-data-commons/4.0.3/spring-boot-data-commons-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-persistence/4.0.3/spring-boot-persistence-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/data/spring-data-commons/4.0.3/spring-data-commons-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-hibernate/4.0.3/spring-boot-hibernate-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-jpa/4.0.3/spring-boot-jpa-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-orm/7.0.5/spring-orm-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/data/spring-data-jpa/4.0.3/spring-data-jpa-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-tx/7.0.5/spring-tx-7.0.5.jar:/home/gabriel/.m2/repository/org/springframework/spring-aspects/7.0.5/spring-aspects-7.0.5.jar:/home/gabriel/.m2/repository/org/aspectj/aspectjweaver/1.9.25.1/aspectjweaver-1.9.25.1.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-jdbc/4.0.3/spring-boot-jdbc-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-sql/4.0.3/spring-boot-sql-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-transaction/4.0.3/spring-boot-transaction-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/spring-jdbc/7.0.5/spring-jdbc-7.0.5.jar:/home/gabriel/.m2/repository/org/projectlombok/lombok/1.18.42/lombok-1.18.42.jar:/home/gabriel/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.4/jakarta.xml.bind-api-4.0.4.jar:/home/gabriel/.m2/repository/jakarta/activation/jakarta.activation-api/2.1.4/jakarta.activation-api-2.1.4.jar:/home/gabriel/.m2/repository/org/springframework/spring-core/7.0.5/spring-core-7.0.5.jar:/home/gabriel/.m2/repository/commons-logging/commons-logging/1.3.5/commons-logging-1.3.5.jar:/home/gabriel/.m2/repository/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar:/home/gabriel/.m2/repository/io/jsonwebtoken/jjwt-api/0.11.5/jjwt-api-0.11.5.jar:/home/gabriel/.m2/repository/io/jsonwebtoken/jjwt-impl/0.11.5/jjwt-impl-0.11.5.jar:/home/gabriel/.m2/repository/io/jsonwebtoken/jjwt-jackson/0.11.5/jjwt-jackson-0.11.5.jar:/home/gabriel/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.20.2/jackson-databind-2.20.2.jar:/home/gabriel/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.20/jackson-annotations-2.20.jar:/home/gabriel/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.20.2/jackson-core-2.20.2.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-actuator/4.0.3/spring-boot-starter-actuator-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-starter-micrometer-metrics/4.0.3/spring-boot-starter-micrometer-metrics-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-micrometer-metrics/4.0.3/spring-boot-micrometer-metrics-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-micrometer-observation/4.0.3/spring-boot-micrometer-observation-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-actuator-autoconfigure/4.0.3/spring-boot-actuator-autoconfigure-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-actuator/4.0.3/spring-boot-actuator-4.0.3.jar:/home/gabriel/.m2/repository/org/springframework/boot/spring-boot-health/4.0.3/spring-boot-health-4.0.3.jar:/home/gabriel/.m2/repository/io/micrometer/micrometer-observation/1.16.3/micrometer-observation-1.16.3.jar:/home/gabriel/.m2/repository/io/micrometer/micrometer-commons/1.16.3/micrometer-commons-1.16.3.jar:/home/gabriel/.m2/repository/io/micrometer/micrometer-jakarta9/1.16.3/micrometer-jakarta9-1.16.3.jar:/home/gabriel/.m2/repository/io/micrometer/micrometer-core/1.16.3/micrometer-core-1.16.3.jar:/home/gabriel/.m2/repository/org/hdrhistogram/HdrHistogram/2.2.2/HdrHistogram-2.2.2.jar:/home/gabriel/.m2/repository/org/latencyutils/LatencyUtils/2.0.3/LatencyUtils-2.0.3.jar com.doctour.doctourbe.DoctourBeApplication
```
~~ale to pewnie nie jest potrzebne, powinien starczyć jakiś ~~
```
mvnw clean package
java -jar target/whatever-sie-tu-stworzy
```

~~Powodzenia!~~

1. Zainstaluj docker compose
2. Uzupełnij envy 
   - nazwy i hasła do baz danych dowolne, to jest tworzone na podstawie tego co podasz
   - do rzeczy mailowych polecam mailtrapa
   - frontend_url - url na którym będzie twój frontend, śłuży do linkowania do niego w mailach
   - JWT_SECRET - jakiś długi, losowy string

# Dokumentacja API

---

## Featuresy:

- **dostępność** - każdy lekarz może rozpisać swój grafik na każdy tydzień
- **wizyty** - użytkownicy mogę się wpisywać lekarzowi w ten grafik, jednocześnie nie nachodząc na siebie
- **lokalizacje** - każdy lekarz może zapisać kiedy jest w wybranych lokalizacjach. Użytkownicy mogą również szukać lekarzy, którzy praktykują w ich okolicy
- **powiadomienia** - użytkownicy dostają mailem powiadomienia o zmianie terminu wizyty oraz przypomnienie dzień przed wizytą (z *pięknym* zegarkiem odliczającym do tego terminu!)
- **Global Gender Index** - jeżeli użytkownikowi nie odpowiada żadna z podanych opcji płci, może on dodać własną opcję, która będzie później dostępna dla wszystkich innych użytkowników
- **specjalizacje** - lekarze mogą deklarować swoje specjalizacje

---

*Na start masz stworzonych dwóch użytkoników, bob@gmail.com i alice@gmail.com, obaj z hasłem **153753aB!** . Alice jest lekarzem, a Bob pacjentem.*

## Endpointy:

- ### /api/auth (autentykacja):
    - **POST /register** - tu wyślij
       ```
       {
            "email": "bob.smith@gmail.com"
            "username": "bob smith",
            "password": "verySaf3Password",
            "role": "ROLE_CUSTOMER"|"ROLE_DOCTOR",
            "genderId": 1
       }
       ```
      żeby zarejestrować użytkownika. Wysyła do nowo zarejestrowanego użytkownika maila z linkiem aktywacyjnym  (
      /activateAccount?token=ugrigiyvuy4v4yivi4y)  
      błędy:
        - ROLE_INVALID - nie ma takiej roli
        - GENDER_INVALID - nie ma takiej płci w bazie
    - **POST /activate**  - tu wyślij
        ```
        {
        token: '3jgvkwhbkhn3fbveiygv4uiorvv4gf4g3"
      }
      ```
      token jest podany jako parametr GET w linku aktywacyjnym. Konto zostanie wtedy aktywowane

    - **POST /login** - tu wyślij
      ```
      {
          "email": "bob",
          "password": "verySaf3Password"
      }
      ```  
      przy pomyślnym logowaniui dopina ciacha accessToken i refreshToken
      błędy:
        - USER_NOT_FOUND - nie ma użytkownika o takiej nazwie i haśoe
        - USER_NOT_ACTIVE
        - **/refresh** - tutaj prosisz o nowego access tokena (wyguglaj JWT refresh/access jak nie wiesz o co cho  
          wysyłasz pustego requesta i on ustawia ciacho accessToken
          błędy:
            - TOKEN_INVALID - wysłanego accessTokena nie ma w bazie
    - **POST /logout** - invaliduje ciacha, efektywnie wylogowywując użytkownika
- ## /api/location
    - **GET** - zwraca wszystkie lokalizacje
    - **POST**
  ```
  {
    name:  "Gabinety Dentystystyczny Szeroki Uśmiech",
    description: "Jeden z top 60 gabinetów w powiacie",
    latitude: 50.0967598,
    longitude: 20.1745166
  }
    ```

### /api/availability (dostępności lekarzy)

- **POST** *(wymaga roli DOCTOR)* — dodaje slot dostępności dla zalogowanego lekarza.

  ```json
  {
      "locationId": 1,
      "start": "08:00",
      "end": "16:00",
      "dayOfWeek": 1
  }
  ```

  `dayOfWeek`: 1 = poniedziałek, 7 = niedziela (ISO 8601)

  Błędy:
    - `LOCATION_INVALID` — nie ma lokalizacji o podanym id
    - `AVAILABILITY_TAKEN` — slot nakłada się na inny istniejący slot tego lekarza
    - `AVAILABILITY_END_BEFORE_START`
    - `AVAILABILITY_START_IS_END`

- **PUT /{uuid}** — aktualizuje slot dostępności.

  ```json
  {
      "locationId": 1,
      "start": "09:00",
      "end": "17:00"
  }
  ```

  Błędy:
    - `AVAILABILITY_NOT_EXIST` — nie ma slotu o podanym uuid
    - `AVAILABILITY_NO_ACCESS` — slot nie należy do zalogowanego użytkownika
    - `LOCATION_NOT_EXIST`

- **DELETE /{uuid}** — usuwa slot dostępności.

  Błędy:
    - `AVAILABILITY_NOT_EXIST`
    - `AVAILABILITY_NO_ACCESS`

---

### /api/appointment 

- **GET** — zwraca listę wizyt zalogowanego użytkownika (lekarz widzi swoje wizyty jako doktor, pacjent jako pacjent).

- **GET /{uuid}** — zwraca szczegóły jednej wizyty.

  Błędy:
    - `APPOINTMENT_NOT_FOUND`

- **POST** *(wymaga roli CUSTOMER)* — tworzy wizytę.

  ```json
  {
      "doctorUuid": "550e8400-e29b-41d4-a716-446655440000",
      "locationId": 1,
      "date": "2025-06-15",
      "start": "10:00",
      "end": "10:30"
  }
  ```

  Błędy:
    - `USER_NOT_FOUND` — zalogowany użytkownik nie istnieje w bazie
    - `USER_DOCTOR_NOT_FOUND` — nie ma lekarza o podanym uuid
    - `LOCATION_NOT_FOUND`
    - `APPOINTMENT_TAKEN` — lekarz ma już wizytę w tym przedziale czasowym
    - `APPOINTMENT_NOT_AVAILABLE` — lekarz nie ma slotu dostępności pokrywającego podany czas i lokalizację
    - `APPOINTMENT_END_BEFORE_START`
    - `APPOINTMENT_START_IS_END`

- **PUT /{uuid}** — aktualizuje datę/czas wizyty. Pola opcjonalne — pominięte (null) zachowują poprzednią wartość. Jeżeli updatuje lekarz, użytkownik dostaje maila o zmianie

  ```json
  {
      "date": "2025-06-16",
      "start": "11:00",
      "end": "11:30"
  }
  ```

  Błędy:
    - `APPOINTMENT_NOT_EXIST`
    - `APPOINTMENT_NO_ACCESS` — wizyta nie należy do zalogowanego użytkownika (ani jako pacjent, ani jako lekarz)
    - `APPOINTMENT_NOT_AVAILABLE`
    - `APPOINTMENT_TAKEN`

- **DELETE /{uuid}** — usuwa wizytę.

  Błędy:
    - `APPOINTMENT_NOT_EXIST`
    - `APPOINTMENT_NO_ACCESS`

---

### /api/doctor (lekarze)

- **GET** — zwraca listę wszystkich lekarzy.

- **GET /{uuid}** — zwraca dane jednego lekarza.

  Błędy:
    - `USER_NOT_FOUND`

- **POST /specialization** *(wymaga roli DOCTOR)* — dodaje specjalizację do profilu zalogowanego lekarza.

  ```json
  {
      "specializationId": 3
  }
  ```

  Błędy:
    - `SPECIALIZATION_NOT_FOUND`

- **DELETE /specialization/{id}** *(wymaga roli DOCTOR)* — usuwa specjalizację z profilu zalogowanego lekarza.

  Błędy:
    - `SPECIALIZATION_NOT_FOUND`

---

### /api/gender (płcie)

- **GET** — zwraca wszystkie płcie.

- **GET /{id}** — zwraca jedną płeć.

  Błędy:
    - `GENDER_INVALID`

- **POST** — tworzy nową płeć.

  ```json
  {
      "name": "Mężczyzna",
      "shortname": "M"
  }
  ```

  Błędy:
    - `GENDER_NAME_TAKEN`
    - `GENDER_SHORTNAME_TAKEN`

---

### /api/location (lokalizacje)

- **GET** — zwraca wszystkie lokalizacje.

- **GET /{id}** — zwraca jedną lokalizację.

  Błędy:
    - `LOCATION_NOT_FOUND`

- **GET /at?latitude=&longitude=&radius=** — zwraca lokalizacje w podanym promieniu od punktu.

  Parametry query:
    - `latitude` — szerokość geograficzna
    - `longitude` — długość geograficzna
    - `radius` — promień w metrach

- **POST** *(wymaga roli DOCTOR)* — tworzy lokalizację. `city`, `address`, `postalCode` są opcjonalne.

  ```json
  {
      "name": "Gabinet Dentystyczny Szeroki Uśmiech",
      "description": "Jeden z top 60 gabinetów w powiacie",
      "latitude": 50.0967598,
      "longitude": 20.1745166,
      "city": "Kraków",
      "address": "ul. Przykładowa 1",
      "postalCode": "30-001"
  }
  ```

---

### /api/specialization (specjalizacje)

- **GET** — zwraca wszystkie specjalizacje.

- **GET /{id}** — zwraca jedną specjalizację.

  Błędy:
    - `SPECIALIZATION_NOT_FOUND`

- **POST** *(wymaga roli DOCTOR)* — tworzy specjalizację. `description` jest opcjonalne.

  ```json
  {
      "name": "Kardiologia",
      "description": "Choroby serca i układu krążenia"
  }
  ```

  Błędy:
    - `SPECIALIZATION_EXISTS` — specjalizacja o tej nazwie już istnieje

---
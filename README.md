# payment app

payment processing service using database sharding

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySQL](https://www.mysql.com)

## Running the application locally

database settings:
db.host=localhost:3306
db.user=root
db.pass=root

1. Execute script create-tables.sql
2.
 ```shell
   mvn spring-boot:run
   ```
or execute the `main` method in the `ru.yamoney.payments.PaymentsApplication` class from your IDE.
# Spring Boot Rest Test

## To compile
mvn clean install

## To run
mvn spring-boot:run

## To package and execute in standalone
mvn clean package
java -jar target/sb-test.jar

## Get token
```
curl -X POST --user 'sbtest:s3cr3t' -d 'grant_type=password&username=mariano@tuchorc.com.ar&password=password' http://localhost:8080/sbtest/oauth/token
```

## Example commands
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -X GET http://localhost:8080/sbtest/all/
```

## Postman collection

In support directory there is a postman collection where you can edit the value of the collection variable "access-token" and test all services.
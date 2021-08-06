##URL Shortener

This is standalone maven application.

Assumptions:
----------
1) No fixed length for short url. Length of short string generated from long url could range from 1 to length of range of long data type.
2) Non functional requirement are not considered.


Steps to run :
1. mvn clean package
2. java -jar url-shortener-0.0.1-SNAPSHOT.jar

To run tests:
1. mvn clean test 

Swagger UI:
Swagger-ui can be accessed from http://localhost:8080/swagger-ui.html

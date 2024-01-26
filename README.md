# Ripasso_Spring_java

# Stuff that you need
- Postgres
- Java 21
- Spring 3.2.2 Spring web 6.1.3
- Postman (help testing API)

Create /resource/application.properties with:

spring.datasource.url=jdbc:postgresql://localhost:5432/steam
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

#This will create table automatically in your database
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true



#error handling
server.error.include-exception=true
#server.error.include-stacktrace=always


# Link per collezioni postman
## Per auth
https://elements.getpostman.com/redirect?entityId=32519712-89f9f680-33b8-47aa-b8ae-199b1794291f&entityType=collection
## Per gameController
https://elements.getpostman.com/redirect?entityId=32519712-44ef3225-2e76-4d35-81fb-ebca8ea68649&entityType=collection
## Per UserController
https://elements.getpostman.com/redirect?entityId=32519712-9491cbfb-1613-4e42-a3ab-a169376f8553&entityType=collection

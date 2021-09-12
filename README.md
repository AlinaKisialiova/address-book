# Address Book App
The application provides functionality for operating with addresses for a person.

# Technologies
* Java 11, Spring Boot 2.5.2, Spring JPA, Lombok
* Spring Boot Test, JUnit, BDDMockito
* Maven, JoCoCo
* PostgreSQL (for prod), H2 (for testing)
* Docker, docker-compose
* REST, OpenAPI (Swagger)

# Prerequisites
* Docker to run the app
* Postman, insomnia, curl, etc tools to send requests
* Access to dockerHub to download the app docker image

# API

![Alt text](./api.jpg/?raw=true "Address Book API")

![Alt text](./response_codes.jpg/?raw=true "API Response Codes")

![Alt text](./models.jpg/?raw=true "Address Book Models")

# Running
#### Use the last version of image from dockerHub

```
docker-compose up
```

#### Use locally built image
```
mvnw clean install
```

Uncomment these line in docker-compose file

```
#    build:
#      context: .
```
```
docker-compose up
```
# Implementation notes
* DTO objects are not used because entities contain the same set fields on db, service and controller layer.
* Code coverage 60%. Code coverage approach was to demonstrate ability to create different types of tests: unit (db, layer, controller), integration, and cover main cases.
* No UI, because I am a backend developer

# Author
Alina Kisialiova
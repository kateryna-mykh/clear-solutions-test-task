# test assignment


**Endpoints**
- POST /users
- PATCH /users
- PUT /users
- DELETE /users/{id}
- GET /users/by-birth-range

##Technologies
- Java 17
- Spring Boot, Spring Data JPA
- H2

##Input Json Example
```
{
    "email":"kbenoit0@listmanage.com",
    "firstName":"Kin",
    "lastName":"Benoit",
    "birthDate":"1996-30-07",
    "address":"55205 Banding Lane",
    "poneNumber":"6981169871"
}
```

## Installation and Launchfirst_name
1. Run the project with Maven.
2. For connection to db use [H2 console](http://localhost:8080/h2-console)
3. For requests use Postman.
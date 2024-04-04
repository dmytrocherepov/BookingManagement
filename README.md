# <h1 align="center">Book Management Service</h1>

The primary objective of the Booking management is to provide an efficient and user-friendly platform for managing books. This platform allows customers to post , update , delete and get books.







## Project Technologies
___
`Techonologies are use to buiild Online Book Store: `
- **Programming Language:** `Java 17`
- **Spring Framework:** `Spring Boot v3.1.4, Spring Data`
- **Database Management:** `PostgreSQL 16.2, Hibernate`
- **Testing:** `JUnit 5, Mockito`
- **Additional instruments:** `Gradle, Lombok, Mapstruct, Protobuf`

<a name="entities"></a>
## Entities
_____
1. **BookEntity** - represents book .



<a name="endpoints"></a>
## Endpoints
___


### Book Management

| Method | Endpoint         | Description                                             | 
|--------|------------------|---------------------------------------------------------|
| GET    | /books/{id}      | Get book by id if present                               | 
| POST   | /books           | Creates a new book                                      | 
| PUT    | /books/{id}      | Updates book with id (if exists)                        | 
| DELETE | /books/{id}      | Deletes a book with id (if exists)                      |


## Demonstration
___

Here you can watch a little guide about Book Store functionality:

In progress...


## How to use
___

1. **Clone the repository from GitHub:** [GitHub repositry](https://github.com/dmytrocherepov/BookStoreApp)
2. **Run `./gradlew build` in the terminal**
3. **Run grpc-server and grpc-client , the application should be running locally at http://localhost:8080**
4. **You can check the functionality using BloomRPC (using proto file) or Postman for sending request to localhost:8080**


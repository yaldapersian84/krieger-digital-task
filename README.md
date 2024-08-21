# Document and Author Management Web Application

# About the project

<ul style="list-style-type:disc">
  <li>User can register and login</li>
  <li>Admin can create, update, delete and read documents and authors</li>
  <li>User can read documents and authors</li>
</ul>

7 services, listed below, have been developed as part of this project.

- Document Service(Main service for CRUDs)
- User Service
- Notification Service (Consumer)
- Config Server
- Discovery Server(Eureka)
- API Gateway
- Auth Service

### Used Technologies

* Core
    * Spring
        * Spring Boot
        * Spring Security
            * Spring Security JWT
            * Authentication
            * Authorization
        * Spring Data
            * Spring Data JPA
            * PostgreSQL
        * Spring Cloud
            * Spring Cloud Gateway Server
            * Spring Cloud Config Server
            * Spring Cloud Config Client
    * Netflix
        * Eureka Server
        * Eureka Client
* Database
    * PostgreSQL
* Kafka
* Docker
* Validation
* Modelmapper
* Openapi UI
* Lombok
* Log4j2

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Valid Request Body</th>
      <th>Valid Request Params</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/v1/auth/register</td>
      <td>Register for User</td>
      <td><a href="#register">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/v1/auth/login</td>
      <td>Login for User and Admin</td>
      <td><a href="#login">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/user/getAll</td>
      <td>Get all user</td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/user/getUserById/{id}</td>
      <td>Get user by id</td>
      <td></td>
      <td><a href="#getUserById">Info</a></td>
  </tr>
 <tr>
      <td>GET</td>
      <td>/v1/user/getUserByEmail/{email}</td>
      <td>Get user by email</td>
      <td></td>
      <td><a href="#getUserByEmail">Info</a></td>
  </tr>
 <tr>
      <td>PUT</td>
      <td>/v1/user/update</td>
      <td>Update user</td>
      <td><a href="#updateUser">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>/v1/user/deleteUserById/{id}</td>
      <td>Delete user by id</td>
      <td></td>
      <td><a href="#deleteUserById">Info</a></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/v1/document-service/documents</td>
      <td>Document create</td>
      <td><a href="#documentCreate">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/document-service/documents</td>
      <td>Get all documents</td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/document-service/documents/{id}</td>
      <td>Get document by id</td>
      <td></td>
      <td><a href="#getDocumentById">Info</a></td>
  </tr>
 <tr>
      <td>PUT</td>
      <td>/v1/document-service/documents</td>
      <td>Update document</td>
      <td><a href="#updateDocument">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>/v1/document-service/documents/{id}</td>
      <td>Delete document</td>
      <td></td>
      <td><a href="#deleteDocumentById">Info</a></td>
  </tr>
   <tr>
      <td>POST</td>
      <td>/v1/document-service/authors</td>
      <td>Author create</td>
      <td><a href="#authorCreate">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/document-service/authors</td>
      <td>Get all authors</td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/v1/document-service/authors/{id}</td>
      <td>Get author by id</td>
      <td></td>
      <td><a href="#getAuthorById">Info</a></td>
  </tr>
 <tr>
      <td>PUT</td>
      <td>/v1/document-service/authors</td>
      <td>Update author</td>
      <td><a href="#updateAuthor">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>DELETE</td>
      <td>/v1/document-service/authors/{id}</td>
      <td>Delete author</td>
      <td></td>
      <td><a href="#deleteAuthorById">Info</a></td>
  </tr>
</table>

## Valid Request Body

##### <a id="register"> Register for User

``` 
    http://localhost:8080/v1/auth/register
    
        {
          "username": "string",
          "password": "string",
          "email": "string"
        }
```

##### <a id="login"> Login for User and Admin

```
      http://localhost:8080/v1/auth/login
    
       {
         "username": "string",
         "password": "string"
       }
```

##### <a id="updateUser"> Update User

```
    http://localhost:8080/v1/user/update
    
      {
            "id": "string",
            "username": "string",
            "password": "string",
            "userDetails": {
              "firstName": "string",
              "lastName": "string",
              "phoneNumber": "string",
              "country": "string",
              "city": "string",
              "address": "string",
              "postalCode": "string",
              "aboutMe": "string"
            }
           
        }
    
    Bearer Token : Authorized User or Admin
```

##### <a id="documentCreate"> Create Document

``` 
    http://localhost:8080/v1/document-service/documents
    
    {
  "title": "string",
  "body": "string",
  "authors": [
    "string"
  ],
  "references": [
    {
      "authors": "string",
      "title": "string",
      "publicationYear": 0,
      "journalName": "string",
      "volume": 0,
      "issue": 0,
      "pages": "string",
      "doi": "string"
    }
  ]
}

    
    Bearer Token : Admin Token
```

##### <a id="updateDocument"> Update Document

``` 
    http://localhost:8080/v1/document-service/documents
    
    {
  "firstName": "string",
  "lastName": "string"
}
    Bearer Token : Admin Token
```

##### <a id="authorCreate"> Create Author

``` 
    http://localhost:8080/v1/document-service/authors
    
    {
  "firstName": "string",
  "lastName": "string"
}
    
    Bearer Token : Admin Token
```

##### <a id="authorJob"> Update Author

``` 
    http://localhost:8080/v1/document-service/authors
    
   {
  "firstName": "string",
  "lastName": "string"
}
    Bearer Token : Admin Token
```

## Valid Request Params

##### <a id="getUserById"> Get User By Id

```
    http://localhost:8080/v1/user/getUserById/{id}
    
    Bearer Token : User Token
```

##### <a id="getUserByEmail"> Get User By Email

```
    http://localhost:8080/v1/user/getUserByEmail/{email}
    
    Bearer Token : User Token
```

##### <a id="deleteUserById">Delete User By Id

``` 
    http://localhost:8080/v1/user/deleteUserById/{id}
    
    Bearer Token : Authorized User or Admin
```

##### <a id="getDocumentById"> Get Document By Id

``` 
    http://localhost:8080/v1/document-service/documents/{id}
    
    Bearer Token : User Token
```

##### <a id="deleteDocumentById">Delete Document By Id

``` 
    http://localhost:8080/v1/document-service/documents/{id}
    
    Bearer Token : Admin Token
```

##### <a id="getAuthorById"> Get Author By Id

``` 
    http://localhost:8080/v1/document-service/authors/{id}
    
    Bearer Token : User Token
```

##### <a id="deleteAuthorById">Delete Author By Id

``` 
    http://localhost:8080/v1/document-service/authors/{id}
    
    Bearer Token : Admin Token
```

### 🔨 Run the App

<b>Local</b>

<b>1 )</b> Clone project `git clone https://github.com/yaldapersian84/krieger-digital-task`

<b>2 )</b> Go to the project's home directory :  `cd krieger-digital-task`

<b>3 )</b> Run docker compose <b>`docker compose up`</b></b>

<b>4 )</b> Run <b>Discovery Server</b>

<b>5 )</b> Run <b>API Gateway</b>

<b>6 )</b> Run <b>Config Server</b>

<b>7 )</b> Run other services (<b>auth-service</b>, <b>user-service</b>, <b>document-service</b>, <b>notification-service</b>)

<b>8 )</b> For swagger ui localhost:8080/v1/{service-name}/swagger-ui/index.html</b>

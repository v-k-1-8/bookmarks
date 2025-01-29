
# Bookmarks

Bookmarks is a Spring Boot project that connects to a PostgreSQL database for storing users and their bookmarks using predefined schemas. It provides RESTful endpoints to interact with the stored data.

## Prerequisites
Ensure you have the following installed before running the project:     

- Java 18+    
- Maven 3.9+  
- Spring Boot 
- PostgreSQL (Database)


## Setup & Installation
1. Clone the Repository
```git clone https://github.com/v-k-1-8/bookmarks```    

2. Configure Environment Variables
- Modify ``src/main/resources/application`` properties to configure your PostgreSQL database connection.

3. Build the Project
```mvn clean install```

4. Run the Application
```mvn spring-boot:run```   

5. Verify the Setup
Check url  ```http://localhost:5000/health-check```
## API Reference
### Users

#### Create New User

```http
  POST /create-user
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user` | `JSON` | **Required**. JSON object containing user details |

Request Body Example: 
```
{
     "name": "varun",
     "dob": "2004-31-01T10:00:00",
     "email": "varun@gmail.com",
     "address": "Roorkee"
}
```
Respone Example:
```
{
    "id": 30,
    "name": "varun",
    "dob": "2006-07-01T10:00:00.000+00:00",
    "email": "varun@gmail.com",
    "address": "Roorkee"
}
```
#### Get, Update, Delete User

```http
  GET /user/{id}
  PUT /user/{id}
  DELETE /user/{id} 
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of User to fetch |

#### Get all Users

```http
  GET /users
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of User |

Response: ```List<user>```

### Bookmarks

#### Create New Bookmark

```http
  POST /create-bookmark/user/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userid` | `long` | **Required**. UserId to which bookmark belongs |
| `bookmark` | `JSON` | **Required**. JSON object containing bookmark details |

Request Body Example: 
```
{
     "link": "https://codeforces.com",
     "title": "Codeforces",
     "note": "CP"

}
```
Respone Example:
```
{
    "id": 31,
    "link": "https://codeforces.com",
    "title": "Codeforces",
    "content":<HTML CODE>,
    "note": "CP",
    "userId": 9
}
```
#### Get all Bookmarks

```http
  GET /bookmarks
```

Response: ```List<bookmark>```


#### Get, Delete Bookmark by id

```http
  GET /bookmark/{id}
  DELETE /bookmark/{id}

```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of Bookmark to fetch |

#### Get all Bookmarks of a Userid

```http
  GET /bookmarks/user/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of User |

Response: ```List<bookmark>```

## Configuring Logging Modes
To configure the logging mode, modify the ``log4j2-spring.xml`` file:

1. Open ``src/main/resources/log4j2-spring.xml``.

2. Locate the <Properties> section.

3. Update the logging mode inside the Property tag:
```
<Properties>
    <Property name="logFormat">
        ${logging.mode:<NAME_OF_MODE>Pattern}
        eg: ${logging.mode:simplePattern}
    </Property>
</Properties>
```
## Swagger UI Guide
#### Accessing Swagger UI
- Start the Application
- Go to URL ```http://localhost:5000/swagger-ui.html```


-----

Ensure the application port and Database settings are properly configured in ``application.properties``

-----
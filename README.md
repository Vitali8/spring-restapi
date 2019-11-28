# spring-restapi
Build a Restful CRUD API using Kotlin, Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Docker

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/Vitali8/spring-restapi.git
```

**2. Create MariaDB using Docker**
```bash
docker-compose up
```

**3. Running the App**

Type the following command in your terminal to run the app -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs [WIP]

The app defines following APIs.

For columns:

    GET /columns
    POST /columns
    GET /columns/{id}
    PUT /columns/{id}
    PUT /columns/{id}/addTask
    PUT /columns/{id}/removeTask
    PUT /columns/{targetColumnId}/moveBefore/{otherColumnId}
    PUT /columns/{targetColumnId}/moveAfter/{otherColumnId}
    DELETE /columns/{id}

For tasks:

    GET /tasks
    GET /tasks/{id}
    PUT /tasks/{targetTaskId}/moveBefore/{beforeTaskId}
    PUT /tasks/{targetTaskId}/moveAfter/{afterTaskId}
    GET /columns/{columnId}/tasks/
    POST /columns/{id}/tasks
    GET /columns/{columnId}/tasks/{taskId}
    PUT /columns/{columnId}/tasks/{taskId}
    PUT /columns/{columnFrom}/tasks/{taskId}/moveToColumn/{columnTo}
    DELETE /columns/{columnId}/tasks/{taskId}


You can test them using postman or any other rest client.
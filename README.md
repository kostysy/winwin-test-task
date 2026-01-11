# Auth & Data API Services

This project contains two microservices (`auth-api` and `data-api`) and a PostgreSQL database.  
`auth-api` handles user registration, login, and processing requests. `data-api` provides the transform endpoint.

The services are dockerized and can be run on **Linux** or **Windows** using separate Docker Compose files.

---

## Services

### 1. **auth-api**
- Provides a `POST` `/api/auth/register` endpoint for user registration, accepts `application/json` body:
``` 
{
    "email": "example@example.com"
    "password": "examplepassword"
} 
```
- Provides a `POST` `/api/auth/login` endppoint for user login, accepts `application/json` body":
``` 
{
    "email": "example@example.com"
    "password": "examplepassword"
} 
``` 
Returns a JWT token that should be saved locally for future authentication.
- Provides `/api/process` endpoint with path variable `text`, returns transformed text. Requires an authentication 
with JWT token you received via login, use authentication header" `Authorization: Bearer <Your JWT Token>`. 

### 2. **data-api**
- Provides `POST` API endpoints that `auth-api` consumes witch transforms received text and uses internal token for
authorization between services.   

### 3. **PostgreSQL**
- `users` table contains user `email` and encrypted `password`
- `logs` table contains `inputText`, `transformedText`, `timestamp` and `user_id`

---

## Running the Services

### Features
- Builds both services with one `docker compose` command. 
- Downloads Maven dependencies only once when it's needed.
- Uses health check on `postgres` service before starting dependant service.
- Allows for easy changing of environment variables via `docker compose` command or 
modification of `docker-compose.yml` file.

### Linux

```bash
docker compose -f docker-compose.yml -f docker-compose.linux.yml up --build
```

### Linux

```bash
docker compose -f docker-compose.yml -f docker-compose.windows.yml up --build
```

### Note!
Test task description says that `auth-api` should connect `data-api` via `localhost:8081`
it could be done by using `network-mode: host` parameter if docker runs on Linux but it doesn't
work correctly if you are running Docker Desktop via Windows. That's why we have two different configurations 
for Windows and Linux.

## How to run requests

### Register
```bash
curl -i -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"email": "your@email.com", "password": "yourPassword123"}'
```

Returns with `201` status code if registration is success-full.

### Login
```bash
curl -i -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email": "your@email.com", "password": "yourPassword123"}'
```

Returns with `200` status code if login is success-full and returns JWT Token

### Process
```bash
curl -i -X POST "http://localhost:8080/api/process?text=Your_Test_String" -H "Authorization: Bearer <Your JWT Token>"
```

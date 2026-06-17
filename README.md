## ✨ Portfolio Service

Lightweight Spring Boot service for uploading and downloading client documents (S3-backed).

Features

Upload documents for a client (returns S3 key)

Download documents by S3 bucket and key

Portfolio management endpoints (create portfolio, submit BUY orders)

Asynchronous messaging via Kafka (publishes buy orders / events)

Persistence using AWS RDS (MySQL) for portfolio and related data

Built with Spring Boot and AWS SDK v2 (S3)

Prerequisites

Java 22

Maven 3.6+

AWS credentials with S3 access (via environment variables, profile, or IAM role)

Access to a Kafka broker (bootstrap servers) for messaging

An AWS RDS instance (MySQL) and credentials for the application's datasource

Build & Run

From the project root:

Build:mvn clean package

Run:mvn spring-bootorjava -jar target/portfolio-service-0.0.1-SNAPSHOT.jar

Endpoints

Document endpoints

POST /documents/upload

Content-Type: multipart/form-data

Params:

clientId (string)

file (file)

Response: 200 OK with message containing the uploaded S3 key

Example:curl -v -F "clientId=123" -F "file=@/path/to/document.pdf" http://localhost:8080/documents/upload

GET /documents/download

Query params: bucket, key

Response: raw file bytes with an attachment Content-Disposition header

Example:curl -v "http://localhost:8080/documents/download?bucket=my-bucket&key=path/to/object"

Portfolio controller

Provides endpoints to create a portfolio for a client and submit BUY orders to the trade service.

POST /portfolio/{clientId}/create

Path param: clientId (long)

Response: 200 OK, body: "Portfolio created"

Example:curl -X POST http://localhost:8080/portfolio/123/create

POST /portfolio/{clientId}/buy

Path param: clientId (long)

Body: JSON BuyOrderRequest

symbol (string)

quantity (long)

price (number)

Response: 200 OK, body: "BUY order sent to Trade Service"

Example:curl -X POST -H "Content-Type: application/json" -d '{"symbol":"AAPL","quantity":10,"price":150.50}' http://localhost:8080/portfolio/123/buy

Notes:

Buy orders are forwarded to the configured Trade Service via the PortfolioService. Ensure trade service connectivity and necessary configuration are provided.

Configuration

Typical Spring configuration lives in src/main/resources/application.yml or application.properties. Provide the following configurations as needed:

AWS: region, credentials (or IAM role), and S3 bucket names

Datasource: spring.datasource.url, spring.datasource.username, spring.datasource.password (RDS MySQL)

Kafka: spring.kafka.bootstrap-servers, producer/consumer properties, topics

Secrets: AWS Secrets Manager may be used to store DB credentials (secretsmanager dependency is included)

Ensure network access (VPC, security groups) between the app, RDS, and Kafka broker when running in cloud environments.

Notes

The controller uses an internal DocumentUploadAndDownloadService which interacts with S3 (S3 key returned on upload).

Current controller sets Content-Type to image/jpeg and uses document.pdf as the attachment filename; adjust content type and filename handling in the controller/service as needed.

Kafka: PortfolioService publishes BUY orders/events to Kafka topics; confirm topic names and producer configuration in application properties.

RDS: JPA configuration uses MySQL connector; confirm schema and migrations are applied to the RDS instance before running.

Security: Do not commit credentials. Prefer AWS Secrets Manager or environment variables for sensitive configuration.

Development

Tests: mvn test

OpenAPI UI: springdoc is included (access /swagger-ui.html or /swagger-ui/index.html when running)

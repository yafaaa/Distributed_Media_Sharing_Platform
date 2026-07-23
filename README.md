# Distributed Smart Media Processing & Sharing Platform

Welcome to the Distributed Smart Media Processing & Sharing Platform. This document outlines the architectural blueprint, system design, data flows, and engineering practices applied to build this scalable and robust microservices-based application.

## 🎯 System Overview

The platform provides a scalable infrastructure for users to securely upload, process, manage, and share media assets. Built on modern cloud-native principles, it leverages a microservices architecture to ensure high availability, fault tolerance, and decoupled scalability.

## 🏗️ High-Level System Architecture

The system is composed of several independent, specialized microservices communicating securely behind an API Gateway.

```mermaid
graph TD
    Client[Web/Mobile Client] -->|HTTPS| Gateway(API Gateway)

    Gateway -->|Routes `/auth/**`| Auth[Auth Service]
    Gateway -->|Routes `/media/**`| Media[Media Service]
    Gateway -->|Routes `/share/**`| Share[Sharing Service]

    Auth -.->|Validates/Issues JWT| Gateway
    Auth --> AuthDB[(Auth DB)]

    Media --> MediaDB[(Media DB)]
    Media --> Storage[(Local/Cloud Storage)]

    Share --> ShareDB[(Share DB)]

    classDef service fill:#f9f,stroke:#333,stroke-width:2px;
    class Gateway,Auth,Media,Share service;
```

### Core Components
- **API Gateway (`api-gateway`)**: The single entry point for all client requests. Handles request routing, CORS configuration, and acts as a centralized authentication checkpoint (validating JWTs and propagating identity headers).
- **Auth Service (`auth-service`)**: Manages user identities, secure registration, credential verification, and JWT token issuance.
- **Media Service (`media-service`)**: The heavy-lifting engine responsible for handling multipart file uploads, asynchronous media processing (like image resizing via Thumbnailator or video thumbnail generation), and safe retrieval/deletion.
- **Sharing Service (`sharing-service`)**: Encapsulates the business logic for access control, peer-to-peer sharing permissions, and secure token-based public link generation.
- **Common Library (`common-lib`)**: A shared dependency ensuring cross-service consistency, housing standard DTOs, uniform API response formats, and centralized exception handlers.

## ⚙️ Professional Engineering Practices

The backend architecture incorporates several industry-standard software engineering practices:

- **Microservices Architecture**: Services are decoupled by domain (Authentication, Media, Sharing) to allow independent scaling and deployment.
- **Centralized API Gateway**: Implements the API Gateway pattern to abstract internal microservices, providing a unified frontend and handling cross-cutting concerns like authentication validation globally.
- **Shared Domain Libraries (`common-lib`)**: Enforces DRY (Don't Repeat Yourself) by sharing standardized response wrappers, constants, and custom exception hierarchies across all services.
- **Asynchronous Processing**: Intensive tasks, such as image resizing and thumbnail generation, are handled asynchronously (`@Async`) to ensure non-blocking, responsive API interactions for the end user.
- **Robust Security**: Passwords are securely hashed using `BCrypt`. All stateless service-to-service and client-to-service communications are secured via signed JSON Web Tokens (JWT).
- **Centralized Error Handling**: Utilizing `@ControllerAdvice` in the common library to trap exceptions universally and return consistent, well-formed error responses to clients.

## 🔄 Data Flow Explanations

### 1. Authentication Flow
```mermaid
sequenceDiagram
    participant Client
    participant API_Gateway
    participant Auth_Service
    participant Auth_DB

    Client->>API_Gateway: POST /auth/login {user, pass}
    API_Gateway->>Auth_Service: Forward Request
    Auth_Service->>Auth_DB: Fetch User & Verify BCrypt Hash
    Auth_DB-->>Auth_Service: Verified
    Auth_Service->>Auth_Service: Generate Signed JWT
    Auth_Service-->>API_Gateway: Return AuthResponse (JWT)
    API_Gateway-->>Client: 200 OK + JWT
```

### 2. Media Upload & Async Processing Flow
```mermaid
sequenceDiagram
    participant Client
    participant API_Gateway
    participant Media_Service
    participant Storage
    participant Async_Worker

    Client->>API_Gateway: POST /media/upload (Multipart + JWT)
    API_Gateway->>API_Gateway: Validate JWT & Inject X-Auth-User-Id
    API_Gateway->>Media_Service: Forward Upload Request
    Media_Service->>Storage: Save Original File
    Media_Service->>Async_Worker: Trigger Async Processing (Resize/Compress)
    Media_Service-->>API_Gateway: Return 202 Accepted (Media ID)
    API_Gateway-->>Client: 202 Accepted

    Note over Async_Worker,Storage: Background Process
    Async_Worker->>Storage: Save Processed File
    Async_Worker->>Media_Service: Update Media DB Record (Status: READY)
```

### 3. Secure Media Sharing Flow
```mermaid
sequenceDiagram
    participant Owner
    participant API_Gateway
    participant Sharing_Service
    participant Share_DB
    participant Viewer

    Owner->>API_Gateway: POST /share {mediaId, targetUserId}
    API_Gateway->>Sharing_Service: Forward Request (User=Owner)
    Sharing_Service->>Share_DB: Verify Ownership & Create Share Record (Generate Token)
    Sharing_Service-->>Owner: Return 201 Created (Share Token)

    Viewer->>API_Gateway: GET /shared/{token}
    API_Gateway->>Sharing_Service: Forward Request
    Sharing_Service->>Share_DB: Validate Token & Expiry
    Sharing_Service-->>Viewer: Return Media Access details
```

## 👥 Team & Responsibilities
- **Yeabsira Belete**: `auth-service` (Security & User Identity)
- **Yeabsira Ayele**: `media-service` (Uploads & Processing Engine)
- **Yafet Mickael**: `sharing-service` (Permission Logic & Distribution)

---

## 🗓️ Execution Roadmap
1.  **Step 1**: Complete `common-lib` (DTOs & Exceptions).
2.  **Step 2**: Deploy `auth-service` (The backbone of security).
3.  **Step 3**: Deploy `media-service` (Core functionality).
4.  **Step 4**: Deploy `sharing-service` (The social/sharing layer).
5.  **Step 5**: Finalize `api-gateway` and End-to-End testing.

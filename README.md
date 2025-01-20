<div align="center">

# 💸 Transaction Service

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=your-jenkins-url)](https://jenkins-url)
[![Quality Gate Status](https://img.shields.io/sonar/quality_gate/transaction-service?server=http://54.86.47.1:9000)](http://54.86.47.1:9000)
[![Docker Pulls](https://img.shields.io/docker/pulls/your-repo/transaction-service)](https://hub.docker.com/r/your-repo/transaction-service)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
</div>

## 📋 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Pipeline](#-pipeline)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [Team](#-team)
- [License](#-license)

## 🎯 Overview

A robust transaction processing microservice built with Spring Boot that handles all financial transactions for the FinTechPro platform. This service manages deposits, withdrawals, transfers, and maintains transaction history with real-time event processing.

## ✨ Features

- 💰 **Multiple Transaction Types**
    - Deposits
    - Withdrawals
    - Transfers
- 📊 **Transaction Status Tracking**
- 🔄 **Real-time Processing**
- 📱 **Wallet Integration**
- 📨 **Notification Service**
- 📈 **Transaction History**
- 🔍 **Advanced Filtering**
- 📊 **Status Monitoring**
- 🔐 **Secure Processing**
- 📝 **Audit Logging**

## 🏗 Architecture

The service follows a microservices architecture with the following components:

```
                                 ┌─────────────┐
                            ┌──▶│Wallet Service│
┌──────────────┐    REST    │    └─────────────┘
│  Transaction │◀──────────▶│    ┌─────────────┐
│   Service    │    Kafka   └──▶│Notification  │
└──────────────┘  Messages      │   Service    │
                               └─────────────┘
```

## 🛠 Tech Stack

<div align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white" alt="Kafka" />
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/kubernetes-326ce5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white" alt="Kubernetes" />
</div>

## 📂 Project Structure

```
src/
├── 📱 main/java/com/lsi/transaction/
│   ├── 📊 config/
│   ├── 🎮 controller/
│   │   └── TransactionController.java
│   ├── 📦 dto/
│   │   ├── request/
│   │   └── response/
│   ├── 🏢 entity/
│   │   ├── Deposit.java
│   │   ├── MoneyMethods.java
│   │   ├── Transaction.java
│   │   ├── TransactionStatus.java
│   │   ├── Transfer.java
│   │   └── WithDraw.java
│   ├── 📚 repository/
│   ├── 🔧 service/
│   │   ├── TransactionService.java
│   │   ├── feign_clients/
│   │   └── kafka_service/
│   └── TransactionServiceApplication.java
├── 📝 main/resources/
│   ├── application.yaml
│   └── application-local.yaml
└── 🧪 test/
```

## 🔄 Pipeline

Our CI/CD pipeline ensures secure and reliable deployments:

![CI/CD Pipeline](/images/pipeline-diagram.png)

1. 📥 **Code Checkout**: Source code retrieval
2. 🔍 **SonarQube Analysis**: Code quality and security checks
3. 🏗️ **Maven Build**: Compilation and package creation
4. 🐳 **Docker Build & Push**: Container image creation and ECR upload
5. ☸️ **EKS Deployment**: Kubernetes deployment with proper configurations

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/your-org/transaction-service.git

# Navigate to the project directory
cd transaction-service

# Build the project
mvn clean install

# Run locally
mvn spring-boot:run -Dspring.profiles.active=local

# Run tests
mvn test
```

## 📚 API Documentation

Available endpoints:

```
GET /api/transaction              - Get all transactions
GET /api/transaction/type/{type}  - Get transactions by type (withdraw/deposit/transfer)
GET /api/transaction/wallet/{id}  - Get transactions by wallet ID
GET /api/transaction/status/failed    - Get failed transactions
GET /api/transaction/status/success   - Get successful transactions
GET /api/transaction/status/pending   - Get pending transactions
```

Detailed API documentation is available at `/swagger-ui.html` when running the service.

## ⚙️ Configuration

Key configuration parameters in `application.yaml`:

```yaml
spring:
  application:
    name: transaction-service
  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
    consumer:
      group-id: transaction-group
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

feign:
  wallet-service:
    url: ${WALLET_SERVICE_URL}
```

## 🚢 Deployment

The service is deployed on AWS EKS using Kubernetes manifests in the `k8s/` directory:

- `configmap.yaml`: Environment variables
- `secrets.yaml`: Sensitive data
- `deployment.yaml`: Pod specifications
- `service.yaml`: Service configuration

## 👥 Team

| Avatar                                                                                                  | Name | Role | GitHub |
|---------------------------------------------------------------------------------------------------------|------|------|--------|
| <img src="https://github.com/zachary013.png" width="50" height="50" style="border-radius: 50%"/>        | Zakariae Azarkan | DevOps Engineer | [@zachary013](https://github.com/zachary013) |
| <img src="https://github.com/goalaphx.png" width="50" height="50" style="border-radius: 50%"/>          | El Mahdi Id Lahcen | Frontend Developer | [@goalaphx](https://github.com/goalaphx) |
| <img src="https://github.com/hodaifa-ech.png" width="50" height="50" style="border-radius: 50%"/>       | Hodaifa | Cloud Architect | [@hodaifa-ech](https://github.com/hodaifa-ech) |
| <img src="https://github.com/khalilh2002.png" width="50" height="50" style="border-radius: 50%"/>       | Khalil El Houssine | Backend Developer | [@khalilh2002](https://github.com/khalilh2002) |
| <img src="https://github.com/Medamine-Bahassou.png" width="50" height="50" style="border-radius: 50%"/> | Mohamed Amine BAHASSOU | ML Engineer | [@Medamine-Bahassou](https://github.com/Medamine-Bahassou) |

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
<div align="center">
  <p>Built with ❤️ by the FinTech Team</p>
</div>
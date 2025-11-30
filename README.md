# Spring Boot: Strategy + Composition + Sealed Interfaces (Java 21)

This example demonstrates how to combine the **Strategy Pattern** with the **Composition Pattern** in a Spring Boot (3.3.x) application, while using **Java 21 sealed interfaces** to **restrict core strategies** and **allow safe extensibility**.

See the source under `src/main/java` — in particular `strategy/PaymentStrategy.java` and `strategy/ExtensiblePaymentStrategy.java`.

## Run

```bash
mvn spring-boot:run
```

## Try it

```bash
curl -s -X POST http://localhost:8080/payments   -H 'Content-Type: application/json'   -d '{"type":"CARD","amount":1000,"currency":"INR","attributes":{"cardNumber":"4111111111111112"}}'
```

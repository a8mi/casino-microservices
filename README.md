# Casino Microservices

## Projektbeschreibung
Dieses Projekt ist ein Online-Casino-Backend, das als RESTful Microservice-Architektur entwickelt wurde.
Ziel ist es, die Gefahren von Glücksspiel aufzuzeigen, indem Gewinne, Verluste und Gewinnchancen statistisch dargestellt werden.

Das Projekt wurde im Rahmen des Moduls **B42 Softwareengineering und Softwarearchitekturen** an der HTW Berlin entwickelt.

## Autoren
- Amna Al-Sorani — Banking-Service
- Malte Maier — Roulette-Service
- Veronika Marxer — Slotmachine-Service

## Architektur
Die Anwendung besteht aus drei Microservices, die jeweils in einem eigenen Docker-Container laufen.
Jeder Service hat eine eigene PostgreSQL-Datenbank — insgesamt 6 Container.

| Service | Port | Base URL |
|---|---|---|
| Banking-Service | 8080 | http://localhost:8080/casino/bank/api |
| Roulette-Service | 8081 | http://localhost:8081/casino/roulette/api |
| Slotmachine-Service | 8082 | http://localhost:8082/casino/slots/api |

## Tech-Stack
- Java 21
- Spring Boot 3.4.5
- Maven
- PostgreSQL 15
- Docker & Docker Compose
- JUnit 5 + Mockito
- Swagger (OpenAPI)

## Installation & Starten

### Voraussetzungen
- Docker Desktop installiert
- Git installiert

### Starten
```bash
git clone https://github.com/a8mi/casino-microservices.git
cd casino-microservices
docker-compose up --build
```

### Swagger UI
- Banking-Service: http://localhost:8080/swagger-ui/index.html
- Roulette-Service: http://localhost:8081/swagger-ui/index.html
- Slotmachine-Service: http://localhost:8082/swagger-ui.html

## Tests ausführen

Für jeden Service einzeln:
```bash
cd banking-service
./mvnw test

cd roulette-service
./mvnw test

cd slotmachine-service
./mvnw clean verify
```

## API-Übersicht

### Banking-Service (Port 8080)

#### User
| Methode | Endpunkt | Beschreibung |
|---|---|---|
| GET | /casino/bank/api/users | Alle User abrufen |
| GET | /casino/bank/api/user/{id} | User nach ID abrufen |
| POST | /casino/bank/api/user | Neuen User anlegen |
| PUT | /casino/bank/api/user/{id} | User aktualisieren |
| DELETE | /casino/bank/api/user/{id} | User löschen |
| POST | /casino/bank/api/user/{id}/deposit/{amount}/{decimals} | Geld einzahlen |

#### Transaktionen
| Methode | Endpunkt | Beschreibung |
|---|---|---|
| GET | /casino/bank/api/transactions | Alle Transaktionen |
| GET | /casino/bank/api/transactions/user/{id} | Transaktionen nach User |
| POST | /casino/bank/api/transaction/user/{id} | Transaktion erstellen |
| PUT | /casino/bank/api/transaction/{id} | Transaktion aktualisieren |
| DELETE | /casino/bank/api/transaction/{id} | Transaktion löschen |

#### Statistiken
| Methode | Endpunkt | Beschreibung |
|---|---|---|
| GET | /casino/bank/api/stats | Globale Statistiken |
| GET | /casino/bank/api/stats/user/{id} | User-Statistiken |

### Slotmachine-Service (Port 8082)
| Methode | Endpunkt | Beschreibung |
|---|---|---|
| POST | /casino/slots/api/play | Runde spielen |
| GET | /casino/slots/api/info/rules | Spielregeln |
| GET | /casino/slots/api/info/chances | Gewinnchancen |
| GET | /casino/slots/api/stats | Globale Statistiken |
| GET | /casino/slots/api/stats/user/{id} | User-Statistiken |
| GET | /casino/slots/api/stats/games | Alle Spiele |
| GET | /casino/slots/api/stat/{gameId} | Ein Spiel |
| DELETE | /casino/slots/api/stat/{gameId} | Spiel löschen |

## Lizenz
Creative Commons Attribution 4.0 International (CC BY 4.0)
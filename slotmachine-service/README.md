# Slotmachine Service

Spring Boot microservice for one-round slot-machine games. It validates users and updates balances through the banking service, stores game history in its own PostgreSQL database, publishes statistics, and exposes Swagger UI.

## API

Base URL: `http://localhost:8082/casino/slots/api`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/play` | Play one complete round |
| GET | `/info/rules` | Rules |
| GET | `/info/chances` | Probability and payout table |
| GET | `/stats` | Overall statistics |
| GET | `/stats/user/{userId}` | User statistics |
| GET | `/stats/games` | All games |
| GET | `/stat/{gameId}` | One game |
| DELETE | `/stat/{gameId}` | Delete local history only |

Example:

```bash
curl -X POST http://localhost:8082/casino/slots/api/play \
  -H 'Content-Type: application/json' \
  -d '{"user":1,"bet":2.00}'
```

Swagger UI: `http://localhost:8082/swagger-ui.html`

## Rules and money flow

Three independently weighted reels are generated. Only three identical symbols pay. `amount` is the player's net change:

```text
amount = gross payout - bet
```

The service first verifies the user and balance, then sends one net transaction to the banking service. The game is stored only after the banking transaction succeeds.

The configured theoretical return to player is 91.0845%, with an 8.9155% house edge. These values are also returned by `/info/chances`.

## Run tests

```bash
./mvnw clean verify
```

The JaCoCo HTML report is generated at `target/site/jacoco/index.html`.

## Run with PostgreSQL and Docker

Start the banking service on port 8080, then:

```bash
docker compose up --build
```

The current banking implementation in this repository exposes transaction creation at `/api/transaction/user/{userId}`. Once banking is corrected to the assignment path, start with:

```bash
CASINO_BANKING_TRANSACTION_PATH=/casino/bank/api/transaction/user/{userId} docker compose up --build
```

## Configuration

| Variable | Default |
|---|---|
| `SERVER_PORT` | `8082` |
| `DB_URL` | `jdbc:postgresql://localhost:5434/slotmachine` |
| `DB_USER` | `slotmachine` |
| `DB_PASSWORD` | `slotmachine` |
| `BANKING_BASE_URL` | `http://localhost:8080` |
| `CASINO_BANKING_USER_PATH` | `/casino/bank/api/user/{userId}` |
| `CASINO_BANKING_TRANSACTION_PATH` | `/api/transaction/user/{userId}` |
| `BANKING_CONNECT_TIMEOUT` | `2s` |
| `BANKING_READ_TIMEOUT` | `3s` |


## Author

Veronika Marxer

## Design documentation

- `docs/SPEC-1-slotmachine-service.md`
- `docs/slotmachine-component.puml`
- `docs/play-sequence.puml`

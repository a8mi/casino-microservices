## Optionen für GameRequest:


POST `http://localhost:8081/casino/roulette/api/play`

**erwartet einen RequestBody mit den Attributen:**


    "user": 1,
    "bet_type": "street",
    "bet": [1,2,3],
    "wager": 30

**Für bet_type und bet gibt es folgende Optionen:**

    SINGLE      -> z. B. bet: [5]    
    SPLIT       -> z. B. bet: [2,3]
    CORNER      -> z. B. bet: [7] (nur kleinste Zahl)
    SIXLINE     -> z. B. bet: [13] (nur kleinste Zahl)
    STREET      -> z. B. bet: [1,2,3] (alle Zahlen)
    RED         -> bet nicht nötig
    BLACK       -> bet nicht nötig
    EVEN        -> bet nicht nötig
    ODD         -> bet nicht nötig
    LOW         -> bet nicht nötig
    HIGH        -> bet nicht nötig
    DOZEN       -> z. B. bet: [3] für Zahlen von 25 - 36
    COLUMN      -> z. B. bet: [3] für dritte Kolonne
(Klein- und Großschreibung irrelevant)


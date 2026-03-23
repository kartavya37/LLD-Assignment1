# Running Snake & Ladder

When prompted, enter:
- **Player count** — e.g. `2`
- **Board size n** — e.g. `10` (creates a 10×10 board)
- **Difficulty** — `0` for easy, `1` for difficult

Then keep entering `1` to roll the dice each turn. Enjoy! 🎲

# Snake & Ladder — Design Description

## Overview

This is an object-oriented Java implementation of Snake & Ladder, designed around three core principles: **separation of concerns**, **the Strategy pattern** (for game modes), and **the Singleton pattern** (for the dice). The architecture cleanly separates the *game state* from the *game rules*, making it easy to add new variants without touching core logic.

---

## Class Breakdown

### `Client.java` — Entry Point
The `Client` class is the user-facing layer. It:
- Reads three inputs from the user: `playerCount`, `boardLength` (n), and `difficultyLevel` (0 or 1).
- Delegates game creation to `GameFactory`.
- Runs a simple loop calling `game.makeMove()` until `game.isOver()` returns `true`.

**Design note:** The client knows nothing about rules, board structure, or players — it only interacts through a thin `Game` interface.

---

### `GameFactory.java` — Factory Pattern
`GameFactory.getGame(...)` acts as a **factory** that abstracts game creation. Depending on `difficultyLevel`, it constructs a `Game` object injected with either `EasyGameMode` or `DifficultGameMode`.

**Why this matters:** The client never instantiates a `GameMode` directly. Adding a new mode (e.g., `ExpertGameMode`) only requires a new class and a single `else if` branch in the factory — existing code is untouched.

---

### `Game.java` — Game State & Orchestration
`Game` holds all the **state** of a running game:
- `playerQueue` — a `LinkedList<Player>` acting as a circular queue; the player at the front takes their turn and is re-added to the back (unless they've won).
- `playersRepo` — a full array of all players (used for rendering the board).
- `board`, `dice`, `winners`, `gameOverFlag` — supporting state.

Key method: `makeMove()` — delegates to `gameMode.makeTurn(...)`, then prints the board, then checks if the game is over (≤1 player remaining).

**Design note:** `Game` doesn't implement any rules itself. It is a *coordinator*, not a *rule enforcer*. Rules live entirely in `GameMode` implementations.

---

### `GameMode.java` — Strategy Interface
`GameMode` is the heart of the Strategy Pattern. It defines the contract for any game variant:

```java
void makeTurn(Queue<Player> playerQueue, Board board, Dice dice, ArrayList<Player> winners);
int getPosition(Board board, Dice dice, int oldPosition);
int handleSix(Dice dice);
boolean hasWon(Player player, int boardSize);
void handleVictory(Player player, ArrayList<Player> winners);
```

Both `EasyGameMode` and `DifficultGameMode` implement this interface. The only difference between them is in `handleSix()`.

---

### `EasyGameMode.java` vs `DifficultGameMode.java` — Concrete Strategies

| Behaviour | EasyGameMode | DifficultGameMode |
|---|---|---|
| Roll a 6 | Keep rolling, accumulate sum indefinitely | Roll again; if 6 again, roll once more |
| Three consecutive 6s | Keeps accumulating (no penalty) | Turn is lost (returns 0) |

**`EasyGameMode.handleSix()`:**
```
Roll 6 → keep rolling as long as you get 6s, add all values
```

**`DifficultGameMode.handleSix()`:**
```
Roll 6 → roll again
  If not 6 → return 6 + second roll
  If 6 again → roll third time
    If not 6 → return 6 + 6 + third roll
    If 6 again → return 0 (turn lost)
```

Both modes share identical logic for movement, snake/ladder resolution, and win detection — only `handleSix` differs.

---

### `Board.java` — Board State
`Board` is a **pure data + query class**. It stores:
- `boardLength` — the n in n×n.
- `snakeMap` — `HashMap<Integer, Integer>`: head position → tail position.
- `ladderMap` — `HashMap<Integer, Integer>`: bottom position → top position.

It exposes clean query methods (`hasSnake`, `snakeEnd`, `hasLadder`, `ladderEnd`, `getSize`) and a `printBoard()` method that renders the grid to stdout, correctly handling the **boustrophedon** (snake-path) numbering where even rows go left-to-right and odd rows go right-to-left.

---

### `BoardBuilder.java` — Board Generation (Static Factory)
`BoardBuilder.get(boardLength)` generates a `Board` with **n snakes** and **n ladders** placed randomly, satisfying the constraint that snakes and ladders must span at least one full row (no same-row placement).

Logic:
1. Snake heads are placed in the upper portion; tails are placed at least `boardLength` cells below.
2. Ladder bottoms are placed in the lower portion; tops are placed at least `boardLength` cells above.
3. A `HashSet<Integer>` of used positions prevents collisions between snakes, ladders, and each other.
4. A `maxAttempts` safety valve prevents infinite loops on dense boards.

**Design note:** `BoardBuilder` is separate from `Board` — it handles the *construction concern*, keeping `Board` focused on *state and queries*. This mirrors the **Builder pattern** (though it's a static utility rather than a full builder chain).

---

### `Dice.java` — Singleton Pattern
`Dice` uses a **thread-safe double-checked locking Singleton**:

```java
if (instance == null) {
    synchronized(Dice.class) {
        if (instance == null) {
            instance = new Dice();
        }
    }
}
```

There is exactly one dice object in the entire game. Its `roll()` method returns a random integer from 1–6.

---

### `Player.java` — Simple Entity
`Player` is a **final** class (cannot be subclassed) with:
- `id` — immutable string identifier (e.g., `"P01"`).
- `position` — mutable current position on the board.

Starting position is `1` (as per constructor), though the requirements say position 0 (outside board). This is a minor deviation worth noting.

---

## Design Patterns Used

| Pattern | Where | Why |
|---|---|---|
| **Strategy** | `GameMode` interface + `EasyGameMode` / `DifficultGameMode` | Swap game rules at runtime without changing `Game` |
| **Factory** | `GameFactory` | Decouple game creation from the client |
| **Singleton** | `Dice` | Only one dice object exists; shared across the game |
| **Builder (static)** | `BoardBuilder` | Separate board construction logic from board usage |

---

## Data Flow — One Turn

```
Client calls game.makeMove()
  └─> Game calls gameMode.makeTurn(playerQueue, board, dice, winners)
        └─> GameMode polls player from queue
        └─> GameMode calls dice.roll()
              └─> If 6: calls handleSix(dice) for extra rolls
        └─> GameMode computes new position
              └─> Checks board.hasSnake / board.snakeEnd
              └─> Checks board.hasLadder / board.ladderEnd
              └─> If position > board.getSize(): stay put
        └─> Player position is updated
        └─> If player won: removed from queue, added to winners
        └─> Else: player re-enqueued
  └─> Game prints board
  └─> Game checks if playerQueue.size() <= 1 → sets gameOverFlag
```

---


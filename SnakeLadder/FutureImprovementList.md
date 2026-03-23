## Potential Improvements

1. **Player starting position:** Players start at position `1` in code, but the requirement says position `0` (outside the board). A player should only enter the board on their first roll.
2. **`GameMode` has too many responsibilities:** `hasWon` and `handleVictory` don't actually vary between modes — they could be moved up to `Game`, keeping `GameMode` focused only on movement rules.
3. **Board printing could be a separate concern:** A `BoardRenderer` class would better isolate display logic from board data.
4. **No input validation:** Negative player counts, board sizes of 1×1, or invalid difficulty levels (beyond `throw`) aren't guarded against gracefully.
5. **`Dice` creates a new `Random` every `roll()`:** The `Random` object should be a field, not instantiated on every call.
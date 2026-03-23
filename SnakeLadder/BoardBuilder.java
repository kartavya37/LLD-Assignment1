// Logic of board generation was created with the help of claude code. 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class BoardBuilder {
    private static final Random random = new Random();

    public static Board get(int boardLength) {
        int boardSize = boardLength * boardLength;
        HashMap<Integer, Integer> snakeMap = new HashMap<>();
        HashMap<Integer, Integer> ladderMap = new HashMap<>();

        buildSnakeMap(snakeMap, boardSize, boardLength);
        buildLadderMap(ladderMap, snakeMap, boardSize, boardLength);

        return new Board(boardLength, snakeMap, ladderMap);
    }

    private static void buildSnakeMap(HashMap<Integer, Integer> snakeMap, int boardSize, int boardLength) {
        // Track all occupied positions so nothing collides
        HashSet<Integer> usedPositions = new HashSet<>();
        int count = 0;
        int maxAttempts = boardSize * 10; // safety valve

        while (count < boardLength && maxAttempts-- > 0) {
            // Snake HEAD: must be in upper portion so there's room below it
            // Range: [boardLength + 1, boardSize - 1]  (not cell 1, not last cell)
            int head = randomInRange(boardLength + 1, boardSize - 1);

            // Snake TAIL: must be strictly less than head AND on a different row
            // "different row" means difference >= boardLength (the requirement)
            int tail = randomInRange(1, head - boardLength);

            if (usedPositions.contains(head) || usedPositions.contains(tail)) continue;

            snakeMap.put(head, tail);
            usedPositions.add(head);
            usedPositions.add(tail);
            count++;
        }
    }

    private static void buildLadderMap(HashMap<Integer, Integer> ladderMap,
                                        HashMap<Integer, Integer> snakeMap,
                                        int boardSize, int boardLength) {
        HashSet<Integer> usedPositions = new HashSet<>();
        // Pre-fill with all snake positions so ladders don't collide with them
        usedPositions.addAll(snakeMap.keySet());
        usedPositions.addAll(snakeMap.values());

        int count = 0;
        int maxAttempts = boardSize * 10;

        while (count < boardLength && maxAttempts-- > 0) {
            // Ladder BOTTOM: must be low enough that there's a row above it
            // Range: [2, boardSize - boardLength - 1]
            int bottom = randomInRange(2, boardSize - boardLength - 1);

            // Ladder TOP: must be strictly greater than bottom AND on a different row
            int top = randomInRange(bottom + boardLength, boardSize);

            if (usedPositions.contains(bottom) || usedPositions.contains(top)) continue;

            ladderMap.put(bottom, top);
            usedPositions.add(bottom);
            usedPositions.add(top);
            count++;
        }
    }

    private static int randomInRange(int min, int max) {
        if (min > max) return min; // guard: degenerate range
        return random.nextInt(max - min + 1) + min;
    }
}
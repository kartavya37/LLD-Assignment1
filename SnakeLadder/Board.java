import java.util.HashMap;

public class Board {
    private final int boardLength; 
    private final HashMap<Integer, Integer> snakeMap; // key: start position of snake, value: end position of snake
    private final HashMap<Integer, Integer> ladderMap; 

    //dummy constructor 
    Board(int boardLength, HashMap<Integer, Integer> snakeMap, HashMap<Integer, Integer> ladderMap)
    {
        this.boardLength = boardLength;
        this.snakeMap = snakeMap;
        this.ladderMap = ladderMap; 

    }

    public int getSize(){return boardLength*boardLength;}
    
    public boolean hasSnake(int position){
        return snakeMap.containsKey(position); 
    }

    public boolean hasLadder(int position){
        return ladderMap.containsKey(position); 
    }

    public int snakeEnd(int position){
        if(snakeMap.containsKey(position)) return snakeMap.get(position); 
        return position; 
    }

    public int ladderEnd(int position){
        if(ladderMap.containsKey(position)) return ladderMap.get(position); 
        return position; 
    }

    public void printBoard(HashMap<Integer, String> playerPositions) {
    
    int size = boardLength;
    // int num = size * size;

    for (int row = size - 1; row >= 0; row--) {
        boolean leftToRight = (row % 2 == 0);

        for (int col = 0; col < size; col++) {
            int current;

            if (leftToRight) {
                current = row * size + col + 1;
            } else {
                current = row * size + (size - col);
            }

            String cell = String.valueOf(current);

            
            if (playerPositions.containsKey(current)) {
                cell = playerPositions.get(current);
            }
            
            else if (snakeMap.containsKey(current)) {
                cell = "S";
            }
            
            else if (ladderMap.containsKey(current)) {
                cell = "L";
            }

            System.out.printf("%4s", cell);
        }
        System.out.println();
    }
}
}

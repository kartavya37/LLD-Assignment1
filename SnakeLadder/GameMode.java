import java.util.ArrayList;
import java.util.Queue;

public interface GameMode {
    public void makeTurn(Queue<Player> playerQueue, Board board, Dice dice, ArrayList<Player> winners); 
    public int getPosition(Board board, Dice dice, int oldPosition);
    public int handleSix(Dice dice); // returns new exact position
    public boolean hasWon(Player player, int boardSize); 
    public void handleVictory(Player player, ArrayList<Player> winners); 
}

import java.util.ArrayList;
import java.util.Queue;

public class DifficultGameMode implements GameMode{

    @Override
    public void makeTurn(Queue<Player> playerQueue, Board board, Dice dice, ArrayList<Player> winners) {
        Player player = playerQueue.poll(); 
        player.setPosition(getPosition(board, dice, player.getPosition()));
        if(!hasWon(player, board.getSize())) playerQueue.add(player); 
        else handleVictory(player, winners); 
    }

    @Override
    public int getPosition(Board board, Dice dice, int oldPosition) {
        int val = dice.roll(); 
        int newPosition; 
        if(val==6) newPosition = oldPosition+handleSix(dice);
        else newPosition = oldPosition + val; 
        if(newPosition>board.getSize()) newPosition = oldPosition;
        if(board.hasSnake(newPosition)) newPosition = board.snakeEnd(newPosition);
        if(board.hasLadder(newPosition)) newPosition = board.ladderEnd(newPosition); 
        return newPosition; 
    }

    @Override
    public int handleSix(Dice dice) {
        int val = 6;
        int secondRoll = dice.roll();
        if(secondRoll!=6) return secondRoll+val;
        else{
            int thirdRoll = dice.roll();
            if(thirdRoll!=6) return thirdRoll+secondRoll+val; 
            else return 0; 
        }
    }

    @Override
    public boolean hasWon(Player player, int boardSize) {
        return player.getPosition() == boardSize; 
    }

    @Override
    public void handleVictory(Player player, ArrayList<Player> winners) {
        winners.add(player);  // winners = arraylist
    }
    
}

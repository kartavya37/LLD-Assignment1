import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private final int playerCount;
    private final int boardLength;
    private final GameMode gameMode; 
    private final Queue<Player> playerQueue; 
    private final Player[] playersRepo; 
    private boolean gameOverFlag; 
    final Board board; 
    Dice dice; 
    ArrayList<Player> winners; 
    public Game(int playerCount, int boardLength, GameMode gameMode) {
        this.playerCount = playerCount;
        this.boardLength = boardLength;
        this.gameMode = gameMode; 
        this.playersRepo = getPlayerRepo(playerCount);
        this.playerQueue = getPlayerQueue(playersRepo); 
        System.out.println("Building the board.");
        this.board = BoardBuilder.get(this.boardLength);  
        System.out.println("Board bn gya.");
        System.out.println(playerQueue.size());
        this.gameOverFlag = playerQueue.size()<=1; 
        this.dice = Dice.getInstance(); 
        this.winners = new ArrayList<>(); 
    }
    
    private Player[] getPlayerRepo(int playerCount)
    {
        final Player[] playersRepo = new Player[playerCount]; 
        for(int i=0; i<playerCount; i++)
        {
            Player player = new Player("P"+i+1);  
            playersRepo[i] = player; 
        }
        return playersRepo; 
    }

    private Queue<Player> getPlayerQueue(Player[] playersRepo) {
        final Queue<Player> playerQueue = new LinkedList<Player>(); 
        for(int i=0; i<playerCount; i++) playerQueue.add(playersRepo[i]);
        return playerQueue; 
    }
    
    public HashMap<Integer, String> playerMapBuilder(Player[] playersRepo)
    {
        HashMap<Integer, String> playerPositionMap = new HashMap<>(); 
        for(int i=0 ; i<playersRepo.length; i++)
        {
            Player player = playersRepo[i];
            if(playerPositionMap.containsKey(player.getPosition())) continue; 
            else playerPositionMap.put(player.getPosition(), player.getId()); 
        }
        return playerPositionMap;
    }

    public boolean isOver() {return gameOverFlag;}
    public void makeMove(){
        gameMode.makeTurn(playerQueue, board, dice, winners); 
        board.printBoard(playerMapBuilder(playersRepo));
        if(playerQueue.size()<=1){
            gameOverFlag = true;
            gameOver(); 
        }
    }
    private void gameOver(){
        System.out.println("Game is over.");
    }
}

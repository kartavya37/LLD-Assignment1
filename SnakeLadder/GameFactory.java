
public class GameFactory {

    public static Game getGame(int playerCount, int boardLength, int difficultyLevel) {
         
        if(difficultyLevel==0) return new Game(playerCount, boardLength, new EasyGameMode()); 
        else if(difficultyLevel==1) return new Game(playerCount, boardLength, new DifficultGameMode()); 
        else throw new IllegalArgumentException("Invalid difficulty level: "+difficultyLevel);
    }
}

import java.util.Scanner;

public class Client
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        System.out.println("Please enter player count: ");
        int playerCount = sc.nextInt();
        System.out.println("Please enter value of \"n\" for BoardSize of n*n: ");
        int boardLength = sc.nextInt();
        System.out.println("Please enter difficulty level, 0 means easy and 1 means difficult: ");
        int difficultyLevel = sc.nextInt(); 
        
        System.out.println("Making the game.");
        Game game = GameFactory.getGame(playerCount, boardLength, difficultyLevel); 
        System.out.println("Game Created!");
        while (!game.isOver()) {
            System.out.println("Enter 1 to make move: ");
            int n = sc.nextInt();
            if(n==1) game.makeMove();
            else System.out.println("Invalid input.");
        }
        sc.close();
    }
}
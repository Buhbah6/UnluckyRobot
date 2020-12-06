package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Anthony Nadeau
 */
public class UnluckyRobot {
    public static void main(String[] args) {
        int totalScore = 300;
        int itrCount = 0;
        int reward;
        char direction;
        int x = 0;
        int y = 0;
        do {
            displayInfo(x, y, itrCount, totalScore);
            direction = inputDirection();
            if (!doesExceed(x, y, direction)) {
                switch (direction) {
                    case 'u':
                    case 'U':
                        ++y;
                        break;
                    case 'd':
                    case 'D':
                        --y;
                        break;
                    case 'r':
                    case 'R':
                        ++x;
                        break;
                    case 'l':
                    case 'L':
                        --x;
                        break;        
                }
            }
            else if (doesExceed(x, y, direction)) {
                System.out.println("Exceed Boundary, -2000 damage applied");
                totalScore -= 2000;
            }
            reward = reward();
            reward = punishOrMercy(direction, reward);
            totalScore += reward;
            if (direction == 'l' || direction == 'd' || direction == 'r')
                totalScore -= 50;
            else
                totalScore -= 10;
            System.out.println("");
            itrCount++;
        }
        while (!isGameOver(x, y, totalScore, itrCount));
        evaluation(totalScore);   
    }
    
    /**
     * Prints the current info of the user and their state in the game.
     * @param x the current x coordinate 
     * @param y the current y coordinate
     * @param itrCount the number of iterations passed
     * @param totalScore the total score earned by the user
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {      
        String currentXY = String.format("%s%d%s%d%s", "(X=", x, " ,Y=", y, ")");
        System.out.printf("%s %s %s: %d %s: %d\n", "For point", currentXY, 
                "at iterations", itrCount, "the total score is", totalScore);
    }
    
    /**
     * Checks to see if the x or y values exceed the boundaries of the game.
     * @param x the current x coordinate
     * @param y the current y coordinate
     * @param direction the direction inputted by the user
     * @return true or false, if the coordinates exceed the boundaries
     */
    public static boolean doesExceed(int x, int y, char direction) {
        if (x == 0 && direction == 'l' || x == 4 && direction == 'r')
            return true;
        else if (y == 0 && direction == 'd' || y == 4 && direction == 'u')
            return true;
        return false;
    }
    
    /**
     * Produces a reward based off of a random number generated.
     * @return the reward produced
     */
    public static int reward() {
        Random rand = new Random();
        int die = rand.nextInt(6) + 1;
        switch (die) {
            case (1):
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case (2):
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case (3):
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case (4):
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case (5):
                System.out.println("Dice: 5, reward: 400");
                return 400;
            case (6):
                System.out.println("Dice: 6, reward: 600");
                return 600;
            default:
                System.out.println("Dice:" + die);
        }
        return 0;
    } 
    
    /**
     * Decides if a negative reward should be removed or not.
     * @param direction the direction inputted by the user
     * @param reward the reward produced with reward()
     * @return the initial reward, or 0
     */
    public static int punishOrMercy(char direction, int reward) {
        Random rand = new Random();
        int coinFlip = 0;
        if (reward < 0 && direction == 'u') { 
            coinFlip = rand.nextInt(2);
            if (coinFlip == 0) {
                System.out.println("Coin: tail | Mercy, the negative reward is removed.");
                return reward = 0;
            }
            else {
                System.out.println("Coin: head | No mercy, the negative reward is applied.");
                return reward;
            }
        }
        return reward;
    }
    
    /**
     * Converts a string to Title Case (Xxxx Xxxx)
     * @param str the string inputted
     * @return the string (str) in Title Case
     */
    public static String toTitleCase(String str) {
        str = str.toLowerCase();
        String firstWord = str.substring(0, 1).toUpperCase() + 
                str.substring(1, str.indexOf(" "));
        String secondWord = str.substring(str.indexOf(" "), 
                str.indexOf(" ") + 2).toUpperCase() + str.substring(str.indexOf(" ") + 2);
        str = firstWord + secondWord;
        return str;
    }
    
    /**
     * Asks for the users name, tells the user if they won or lost.
     * @param totalScore the user's current score
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter your name (only two words): ");
        String name = console.nextLine();
        name = toTitleCase(name);
        if (totalScore >= 2000)
            System.out.printf("%s, %s, %s %d\n", "Victory", name, 
                    "your score is", totalScore);
        else
            System.out.printf("%s, %s, %s %d\n", "Mission failed", name, 
                    "your score is", totalScore);   
    }
    
    /**
     * Asks the user to input a valid direction, keeps asking until one is inputted.
     * @return a valid direction
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        char direction;
        do {
            System.out.print("Please input a valid direction: ");
            direction = console.next().charAt(0);
        }
        while (!isDirectionValid(direction));
        return direction;
    }
    
    /**
     * Checks to see if the game is over.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param totalScore the current score of the user
     * @param itrCount the number of iterations that have passed
     * @return true or false, if the game is over or not.
     */   
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
        if (itrCount > 20)
            return true;
        else if (totalScore < -1000)
            return true;
        else if (totalScore > 2000)
            return true;
        else if (x == 4 && y == 4)
            return true;
        else if (x == 4 && y == 0)
            return true;
        else
            return false;
    }
    
    /**
     * Checks to see if the direction inputted is valid
     * @param direction the direction inputted
     * @return true or false, if the direction is valid.
     */
    public static boolean isDirectionValid (char direction) {
        switch (direction) {
            case 'u':
            case 'U':
            case 'd':
            case 'D':
            case 'l':
            case 'L':
            case 'r':
            case 'R':
                return true;
            default:
                return false;
        }
    }   
}

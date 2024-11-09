import java.util.Scanner;

public class TreasureIsland {

    enum Tile {
        EMPTY, TREE, MOUND, SHOVEL, CHEST, PLAYER
    }

    private static final int MAP_SIZE = 7;
    private static Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
    private static int playerRow = 0;
    private static int playerCol = 0;
    private static boolean hasShovel = false;

    public static void main(String[] args) {
        initializeMap();
        displayMap();  // Display the map after initialization

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Treasure Island! Type 'up', 'down', 'left', or 'right' to move. Type 'pick' to pick up items and 'dig' to dig. Type 'quit' to exit.");

        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "up":
                    move(-1, 0);
                    break;
                case "down":
                    move(1, 0);
                    break;
                case "left":
                    move(0, -1);
                    break;
                case "right":
                    move(0, 1);
                    break;
                case "pick":
                    pickUpShovel();
                    break;
                case "dig":
                    digForTreasure();
                    break;
                case "quit":
                    System.out.println("Thanks for playing! Goodbye.");
                    return;
                default:
                    System.out.println("Invalid command.");
            }

            displayMap(); // Display the map after each command
        }
    }

    private static void initializeMap() {
        // Initialize the map with EMPTY tiles
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = Tile.EMPTY;
            }
        }

        // Place exactly 7 obstacles in a way that leaves an accessible path
        map[0][3] = Tile.TREE;   // Row 0, Column 3
        map[1][5] = Tile.MOUND;  // Row 1, Column 5
        map[2][1] = Tile.TREE;   // Row 2, Column 1
        map[3][4] = Tile.MOUND;  // Row 3, Column 4
        map[4][0] = Tile.TREE;   // Row 4, Column 0
        map[5][3] = Tile.MOUND;  // Row 5, Column 3
        map[6][6] = Tile.TREE;   // Row 6, Column 6

        // Place the shovel and chest in reachable positions
        map[5][1] = Tile.SHOVEL;  // Shovel position
        map[2][5] = Tile.CHEST;   // Treasure chest position

        // Set player's starting position
        map[playerRow][playerCol] = Tile.PLAYER;
    }

    private static void move(int rowChange, int colChange) {
        int newRow = playerRow + rowChange;
        int newCol = playerCol + colChange;

        if (newRow < 0 || newRow >= MAP_SIZE || newCol < 0 || newCol >= MAP_SIZE) {
            System.out.println("You cannot go there because of the ocean.");
            return;
        }

        Tile targetTile = map[newRow][newCol];
        if (targetTile == Tile.TREE) {
            System.out.println("You cannot go there because of a tree.");
            return;
        }
        if (targetTile == Tile.MOUND) {
            System.out.println("You cannot go there because of a mound.");
            return;
        }

        // Move player to new location
        map[playerRow][playerCol] = Tile.EMPTY; // Clear old position
        playerRow = newRow;
        playerCol = newCol;

        // If the player moves to a tile with a shovel or chest, display messages
        if (map[playerRow][playerCol] == Tile.SHOVEL && !hasShovel) {
            System.out.println("There is a shovel at this location.");
        } else if (map[playerRow][playerCol] == Tile.CHEST) {
            System.out.println("There is a treasure chest buried here.");
        }

        // Set the player's new position as PLAYER
        map[playerRow][playerCol] = Tile.PLAYER;
    }

    private static void pickUpShovel() {
        if (map[playerRow][playerCol] == Tile.SHOVEL && !hasShovel) {
            hasShovel = true;
            map[playerRow][playerCol] = Tile.EMPTY;  // Remove the shovel from the map
            System.out.println("You picked up the shovel.");
        } else if (hasShovel) {
            System.out.println("You already have the shovel.");
        } else {
            System.out.println("There is no shovel here.");
        }
    }

    private static void digForTreasure() {
        if (map[playerRow][playerCol] == Tile.CHEST) {
            if (hasShovel) {
                System.out.println("Congratulations, you got the treasure!");
                System.exit(0);
            } else {
                System.out.println("You need a shovel to dig up the treasure.");
            }
        } else {
            System.out.println("There is nothing to dig here.");
        }
    }

    private static void displayMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("P "); // Player position
                } else {
                    switch (map[i][j]) {
                        case TREE:
                            System.out.print("T ");
                            break;
                        case MOUND:
                            System.out.print("M ");
                            break;
                        case SHOVEL:
                            System.out.print("S ");
                            break;
                        case CHEST:
                            System.out.print("C ");
                            break;
                        default:
                            System.out.print(". ");
                            break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

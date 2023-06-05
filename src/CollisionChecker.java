import java.util.ArrayList;

public class CollisionChecker {

    private GamePanel gamePanel;
    private Player player;
    private Maze maze;

    public CollisionChecker(GamePanel gp) {
        gamePanel = gp;
        player = gp.getPlayer();
        maze = gp.getMaze();
    }

    public ArrayList<String> checkTile(Player player) {

        Tile[][] tileMap = maze.getMazeMap();

        int[] currTileCoords = player.getCurrTileCoords();
        Tile currTile = tileMap[ currTileCoords[0] ]  [ currTileCoords[1] ];
        ArrayList<String> availableMoves = currTile.getAvailableMoves();

        return availableMoves;

    }
}

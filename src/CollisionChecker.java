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

        Tile[][] mazeMap = maze.getMazeMap();

        ArrayList<String> availableMoves = new ArrayList<String>();

        int[] coords = player.getCurrTileCoords();


        Tile currTile = mazeMap[coords[0]] [coords[1]];
        availableMoves = (ArrayList<String>) currTile.getAvailableMoves().clone();

        Tile nextTile = null;
        String direction = player.getDirection();

        if(direction.equals("up") && coords[0] != 0) {
            nextTile = mazeMap[coords[0] - 1] [coords[1]];
        } else if (direction.equals("down") && coords[0] != mazeMap.length - 1) {
            nextTile = mazeMap[coords[0] + 1] [coords[1]];
        } else if (direction.equals("left") && coords[1] != 0) {
            nextTile = mazeMap[coords[0]] [coords[1] - 1];
        } else if (direction.equals("right") && coords[1] != mazeMap[0].length - 1) {
            nextTile = mazeMap[coords[0]] [coords[1] + 1];
        }

        if(coords[0] == 0) {
            availableMoves.remove("up");
        }
        if(coords[0] == mazeMap.length - 1) {
            availableMoves.remove("down");
        }
        if(coords[1] == 0) {
            availableMoves.remove("left");
        }
        if(coords[1] == mazeMap[0].length - 1) {
            availableMoves.remove("right");
        }

        if(nextTile != null) {

            ArrayList<String> nextTileAvailableMoves = nextTile.getAvailableMoves();

            if (!nextTileAvailableMoves.contains(player.invertDirection(direction))) {
                availableMoves.remove(direction);
            }
        }

        return availableMoves;
    }
}

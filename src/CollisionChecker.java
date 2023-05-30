public class CollisionChecker {

    private GamePanel gamePanel;

    public CollisionChecker(GamePanel gp) {
        gamePanel = gp;
    }

    public void checkTile(Player player) {

        int playerLeftXCoord = player.getXCoord() + player.getCollisionArea().x;
        int playerRightXCoord = player.getXCoord() + player.getCollisionArea().x +player.getCollisionArea().width;
        int playerTopYCoord = player.getYCoord() + player.getCollisionArea().y;
        int playerBottomYCoord = player.getYCoord() + player.getCollisionArea().y + player.getCollisionArea().height;

        int playerLeftCol = playerLeftXCoord / gamePanel.TILE_SIZE_COL;
        int playerRightCol = playerRightXCoord / gamePanel.TILE_SIZE_COL;
        int playerTopRow = playerTopYCoord / gamePanel.TILE_SIZE_COL;
        int playerBottomRow = playerBottomYCoord / gamePanel.TILE_SIZE_COL;

    }
}

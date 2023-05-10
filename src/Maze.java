public class Maze {

    private Tile[][] mazeMap;
    private int level;

    public Maze(Tile[][] map, int lvl) {
        mazeMap = map;
        level = lvl;
    }

    public Tile[][] getMazeMap() {
        return mazeMap;
    }

    public int getLevel() {
        return level;
    }

}

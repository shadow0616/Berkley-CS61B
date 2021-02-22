package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public abstract class Space {
    protected int width;
    protected int height;
    protected Position entrance;
    protected Position startPos;
    protected int entSide;
    // side: [0][] bottom, [1][] left, [2][] top, [3][] right
    protected Position[][] walls = new Position[4][];
    // side: [0] bottom, [1] left, [2] top, [3] right
    protected Position[] exits = new Position[4];
    // clockwise, start from [0] bottom left (startPos)
    protected Position[] corners = new Position[4];
    // [i][j], i: row, j: column
    protected Position[][] floors;
    protected Random random;

    public Space() {
    }

    public void assignCorners() {
        corners[0] = startPos;
        corners[1] = new Position(startPos.x, startPos.y + height - 1);
        corners[2] = new Position(startPos.x + width - 1, startPos.y + height - 1);
        corners[3] = new Position(startPos.x + width - 1, startPos.y);
    }

    public void assignWalls() {
        for (int side = 0; side < 4; side += 1) {
            if (side % 2 == 0) {
                walls[side] = new Position[width - 2];
                for (int i = 0; i < width - 2; i += 1) {
                    switch (side) {
                        case 0:
                            walls[side][i] = new Position(i + startPos.x + 1, startPos.y);
                            break;
                        case 2:
                            walls[side][i] =
                                    new Position(i + startPos.x + 1, startPos.y + height - 1);
                            break;
                        default: throw new RuntimeException("unrecognized side number");
                    }
                }
            } else {
                walls[side] = new Position[height - 2];
                for (int k = 0; k < height - 2; k += 1) {
                    switch (side) {
                        case 1: walls[side][k] = new Position(startPos.x, k + startPos.y + 1);
                            break;
                        case 3: walls[side][k] =
                                new Position(startPos.x + width - 1, k + startPos.y + 1);
                            break;
                        default: throw new RuntimeException("unrecognized side number");
                    }
                }
            }
        }
    }

    public void assignFloors() {
        floors = new Position[width - 2][height - 2];
        for (int i = 0; i < width - 2; i += 1) {
            for (int j = 0; j < height - 2; j += 1) {
                floors[i][j] = new Position(i + startPos.x + 1, j + startPos.y + 1);
            }
        }
    }

    public void drawSpace(TETile[][] world) {
        TETile wallTile = Tileset.WALL;
        TETile floorTile = Tileset.FLOOR;
        TETile entTile = Tileset.FLOWER;
        TETile startTile = Tileset.WATER;
        TETile cornerTile = Tileset.MOUNTAIN;
        // draw walls
        for (Position[] side: walls) {
            for (Position pos: side) {
                world[pos.x][pos.y] =
                        TETile.colorVariant(wallTile, 32, 32, 32, random);
            }
        }
        // draw floors
        for (Position[] row: floors) {
            for (Position pos: row) {
                world[pos.x][pos.y] =
                        TETile.colorVariant(floorTile, 32, 32, 32, random);
            }
        }
        // draw corners
        for (Position corner: corners) {
            world[corner.x][corner.y] =
                    TETile.colorVariant(wallTile, 32, 32, 32, random);
        }
        // draw openings (for testing)
//        for (Position exit: exits) {
//            if (exit != null) {
//                world[exit.x][exit.y] =
//                        TETile.colorVariant(floorTile, 32, 32, 32, random);
//            }
//        }
        // draw start position & entrance
//        world[startPos.x][startPos.y] =
//                TETile.colorVariant(wallTile, 32, 32, 32, random);
        world[entrance.x][entrance.y] =
                TETile.colorVariant(floorTile, 32, 32, 32, random);
    }

    public void fillExit(TETile[][] world, int side) {
        world[exits[side].x][exits[side].y] =
                TETile.colorVariant(Tileset.FLOOR, 32, 32, 32, random);
    }

    public void drawLockedDoor(TETile[][] world) {
        world[entrance.x][entrance.y] = Tileset.LOCKED_DOOR;
    }

    public void randomExits() {
        for (int i = 0; i < 4; i += 1) {
            if (i != entSide) {
                exits[i] = walls[i][random.nextInt(walls[i].length)];
            }
        }
    }

    public boolean overlapCheck(Space space) {
        Position leftTop1 = this.corners[1];
        Position rightBot1 = this.corners[3];
        Position leftTop2 = space.corners[1];
        Position rightBot2 = space.corners[3];
        if (leftTop1.x > rightBot2.x || leftTop2.x > rightBot1.x) {
            return false;
        }
        if (leftTop1.y < rightBot2.y || leftTop2.y < rightBot1.y) {
            return false;
        }
        return true;
    }

    public boolean inBoundCheck(int W, int H) {
        Position leftTop = this.corners[1];
        Position rightBot = this.corners[3];
        if (leftTop.x < 0 || rightBot.x > W - 1) {
            return false;
        }
        if (leftTop.y > H - 1 || rightBot.y < 0) {
            return false;
        }
        return true;
    }

    public int calcEntSide(int exitSide) {
        switch (exitSide) {
            case 0: return 2;
            case 1: return 3;
            case 2: return 0;
            case 3: return 1;
            default: throw new RuntimeException("unrecognized side number");
        }
    }

    public Position calcNewEnt(Position exit, int exitSide) {
        Position newEnt;
        switch (exitSide) {
            case 0:
                newEnt = new Position(exit.x, exit.y - 1);
                break;
            case 1:
                newEnt = new Position(exit.x - 1, exit.y);
                break;
            case 2:
                newEnt = new Position(exit.x, exit.y + 1);
                break;
            case 3:
                newEnt = new Position(exit.x + 1, exit.y);
                break;
            default: throw new RuntimeException("unrecognized side number");
        }
        return newEnt;
    }
}

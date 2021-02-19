package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class Space {
    public int width;
    public int height;
    public Position entrance;
    public Position startPos;
    public int entSide;
    public Position[][] walls = new Position[4][]; // side: [0][] bottom, [1][] left, [2][] top, [3][] right
    public Position[] exits = new Position[4]; // side: [0] bottom, [1] left, [2] top, [3] right
    public Position[] corners = new Position[4]; // clockwise, start from [0] bottom left (startPos)
    public Position[][] floors; // [i][j], i: row, j: column
    public Random RANDOM;

    public void assignCorners() {
        corners[0] = startPos;
        corners[1] = new Position(startPos.x, startPos.y + height - 1) ;
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
                            walls[side][i] = new Position(i + startPos.x + 1, startPos.y + height - 1);
                            break;
                    }
                }
            } else {
                walls[side] = new Position[height - 2];
                for (int k = 0; k < height - 2; k += 1) {
                    switch (side) {
                        case 1: walls[side][k] = new Position( startPos.x, k + startPos.y + 1);
                            break;
                        case 3: walls[side][k] = new Position(startPos.x + width - 1, k + startPos.y + 1);
                            break;
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
                world[pos.x][pos.y] = TETile.colorVariant(wallTile, 32, 32, 32, RANDOM);
            }
        }
        // draw floors
        for (Position[] row: floors) {
            for (Position pos: row) {
                world[pos.x][pos.y] = TETile.colorVariant(floorTile, 32, 32, 32, RANDOM);
            }
        }
        // draw corners
        for (Position corner: corners) {
            world[corner.x][corner.y] = TETile.colorVariant(cornerTile, 32, 32, 32, RANDOM);
        }
        // draw openings (for testing)
        for (Position exit: exits) {
            if (exit != null) {
                world[exit.x][exit.y] = TETile.colorVariant(floorTile, 32, 32, 32, RANDOM);
            }
        }
        // draw start position & entrance
        world[startPos.x][startPos.y] = TETile.colorVariant(startTile, 32, 32, 32, RANDOM);
        world[entrance.x][entrance.y] = TETile.colorVariant(entTile, 32, 32, 32, RANDOM);
    }

    public void randomExits() {
        for (int i = 0; i < 4; i += 1) {
            if (i != entSide) {
                switch(RANDOM.nextInt(2)) {
                    case 0:
                        break;
                    case 1:
                        exits[i] = walls[i][RANDOM.nextInt(walls[i].length)];
                        break;
                }
            }
        }
    }
}

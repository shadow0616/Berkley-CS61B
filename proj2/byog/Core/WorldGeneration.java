package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.RandomWorldDemo;
import java.util.Random;

public class WorldGeneration {
    private static final int HEIGHT = 50;
    private static final int WIDTH = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        private final int x;
        private final int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Room {
        private final int width;
        private final int height;
        private final Position entrance;
        private final int entSide;
        private Position startPos;
        private Position[][] walls = new Position[4][]; // side: [0][] bottom, [1][] left, [2][] top, [3][] right
        private Position[] exits = new Position[4]; // side: [0] bottom, [1] left, [2] top, [3] right
        private Position[] corners = new Position[4]; // clockwise, start from [0] bottom left (startPos)
        private Position[][] floors; // [i][j], i: row, j: column

        private Room(Position entrance, int entSide) {
            this.entrance = entrance;
            this.entSide = entSide;
            // room size constraint, max: 9 x 9, min: 2 x 2, hard coded for now
             width = RANDOM.nextInt(8) + 2;
             height = RANDOM.nextInt(8) + 2;
//            width = 9;
//            height = 9;
            // randomizes entrance position in the walls of the room
            int entWallPos = this.randomEntrance();
            // calculate the start position of the room (bottom left corner, including walls)
            startPos = this.calcStartPos(entWallPos);
            // assign corners
            this.assignCorners();
            // assign walls
            this.assignWalls();
            // assign floors
            this.assignFloors();
            // create random openings
            this.randomExits();
        }

        private int randomEntrance() {
            int entWallPos;
            switch (entSide % 2) {
                case 0: entWallPos = 1 + RANDOM.nextInt(width - 2); break; // avoid corners
                case 1: entWallPos = 1 + RANDOM.nextInt(height - 2); break; // avoid corners
                default:
                    throw new IllegalStateException("Unexpected value: " + entSide % 2);
            }
            return entWallPos;
        }
        private Position calcStartPos(int entWallPos) {
            switch (entSide) {
                case 0: startPos = new Position(entrance.x - entWallPos, entrance.y); break;
                case 1: startPos = new Position(entrance.x, entrance.y  - entWallPos); break;
                case 2: startPos = new Position(entrance.x - entWallPos, entrance.y  - height + 1); break;
                case 3: startPos = new Position(entrance.x - width + 1, entrance.y  - entWallPos); break;
            }
            return startPos;
        }
        private void assignCorners() {
            corners[0] = startPos;
            corners[1] = new Position(startPos.x, startPos.y + height - 1) ;
            corners[2] = new Position(startPos.x + width - 1, startPos.y + height - 1);
            corners[3] = new Position(startPos.x + width - 1, startPos.y);
        }
        private void assignWalls() {
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
        private void assignFloors() {
            floors = new Position[width - 2][height - 2];
            for (int i = 0; i < width - 2; i += 1) {
                for (int j = 0; j < height - 2; j += 1) {
                    floors[i][j] = new Position(i + startPos.x + 1, j + startPos.y + 1);
                }
            }
        }
        private void drawRoom(TETile[][] world) {
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
        private void randomExits() {
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
    private static class Hallway {
        private int length;
        private int entSide;
        private Position entrance;
        private Position[][] walls = new Position[4][];
        private Position[] exits = new Position[4];
        private Position[] corners = new Position[4];
        private Position[][] floors;

        private Hallway(Position entrance, int entSide) {
            this.entrance = entrance;
            this.entSide = entSide;
            length = RANDOM.nextInt(9) + 1;

        }
        private void assignCorners() {
        }
        private void assignWalls() {
        }
        private void assignFloors() {
        }
        private void drawHallway(TETile[][] world) {
        }
        private void randomExits() {
        }
    }
    public static void main(String[] args) {
        TERenderer test = new TERenderer();
        test.initialize(WIDTH, HEIGHT);
        TETile[][] testWorld = new TETile[WIDTH][HEIGHT];
        RandomWorldDemo.fillWithNothing(testWorld);
        Position testPos = new Position(25, 25);
        WorldGeneration testMap = new WorldGeneration();
        Room testRoom = new Room(testPos, 1);

        testRoom.drawRoom(testWorld);
        test.renderFrame(testWorld);
    }
}

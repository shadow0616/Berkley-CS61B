package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 */
public class RandomWorldDemo {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /**
     * Fills the given 2D array of tiles with NOTHING tiles.
     * @param tiles
     */
    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }


    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.NOTHING;
            default: return Tileset.NOTHING;
        }
    }

    private static TETile randomTileNew() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            case 5: return Tileset.WATER;
            default: return Tileset.GRASS;
        }
    }
    /** Helper class to store a position point (x, y) */
    private static class Position {
        private int x;
        private int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Draw a hexagon on the world. Draw s rows from the bottom of the hexagon.
     * Simultaneously, draw s rows reversely from the top of the hexagon to complete the hexagon.
     * @param world the world to draw on
     * @param s the size of the hexagon
     * @param p the starting position of the hexagon located at lower left corner
     *          (offset is included)
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, int s, Position p, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon size must be greater than 2");
        }
        int yLowerStart = p.y;
        int yUpperStart = yLowerStart + s * 2 - 1;
        int xStart = p.x;
        for (int yAdd = 0; yAdd < s; yAdd += 1) {
            int offSet = RandomWorldDemo.calcOffSet(s, yAdd + 1);
            int rowWidth = RandomWorldDemo.calcWidth(s, yAdd + 1);
            for (int xAdd = 0; xAdd < rowWidth; xAdd += 1) {
                int xCoord = xStart + xAdd + offSet;
                int yCoordLower = yLowerStart + yAdd;
                int yCoordUpper = yUpperStart - yAdd;
                world[xCoord][yCoordLower] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
                world[xCoord][yCoordUpper] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
            }
        }
    }

    /**
     * Calculate the x position offset of each row.
     * @param s the size of the hexagon
     * @param i the row of the hexagon, row number starts from 1
     * @return the offset x position of the row, first row offset is 0
     */
    public static int calcOffSet(int s, int i) {
        if (i > s) {
            throw new IllegalArgumentException("Row number cannot be larger than size");
        }
        return (s - 1) - (i - 1);
    }

    /** Calculate the width of each row represented by i, according to hexagon size s.
     *  Row number starts from 1.
     * @param s the size of the hexagon
     * @param i the row of the hexagon, row number starts from 1
     * @return the width of the row, first row has size s
     */
    public static int calcWidth(int s, int i) {
        if (i > s) {
            throw new IllegalArgumentException("Row number cannot be larger than size");
        }
        return s + (i - 1) * 2;
    }

    public static void posHexAbove(Position p, int s) {
        p.y = p.y + s * 2;
    }

    public static void posHexRightCol(Position p, int s) {
        p.x = p.x + calcWidth(s, 1) + calcOffSet(s, 1);
        p.y = p.y - s;
    }

    public static void posHexLeftCol(Position p, int s) {
        p.x = p.x - calcWidth(s, 1) - calcOffSet(s, 1);
        p.y = p.y - s;
    }

    public static Position posMirror(Position p, int tesS, int s) {
        int offSet = calcOffSet(s, 1);
        int hexSpan = 2 * offSet + calcWidth(s, 1);
        return new Position(p.x + (tesS - 1) * (offSet + hexSpan + 1), p.y);
    }

    public static void drawColumn(TETile[][] world, Position p, int l, int s) {
        Position drawPos = new Position(p.x, p.y);
        for (int i = 0; i < l; i += 1) {
            addHexagon(world, s, drawPos, randomTileNew());
            posHexAbove(drawPos, s);
        }
    }

    public static void hexTesselation(TETile[][] world, Position p, int tesS, int s) {
        Position pMirror = posMirror(p, tesS, s);
        for (int i = 0; i < tesS; i += 1) {
            drawColumn(world, p, tesS + i, s);
            drawColumn(world, pMirror, tesS + i, s);
            posHexRightCol(p, s);
            posHexLeftCol(pMirror, s);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

//        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
//        fillWithRandomTiles(randomTiles);

//        ter.renderFrame(randomTiles);
        TETile[][] drawHex = new TETile[WIDTH][HEIGHT];
        Position startPos = new Position(0, 15);
        Position mirrorPos = posMirror(startPos, 3, 3);
        fillWithNothing(drawHex);
        hexTesselation(drawHex, startPos, 4, 3);
//        drawColumn(drawHex, startPos, 3 , 3);
//        drawColumn(drawHex, mirrorPos, 3, 3);
        ter.renderFrame(drawHex);
//        int offSet = calcOffSet(3, 1);
//        int hexSpan = 2 * offSet + calcWidth(3, 1);
//        System.out.println(2 * (offSet + hexSpan + 1));
    }
}

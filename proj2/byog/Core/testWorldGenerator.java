package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.RandomWorldDemo;
import java.util.Random;

public class testWorldGenerator {
    private static final int HEIGHT = 60;
    private static final int WIDTH = 100;
    private static final long SEED = 2873123;

    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer test = new TERenderer();
        test.initialize(WIDTH, HEIGHT);
        TETile[][] testWorld = new TETile[WIDTH][HEIGHT];
        RandomWorldDemo.fillWithNothing(testWorld);
        WorldGenerator testGen = new WorldGenerator(testWorld, RANDOM);

        testGen.generateWorld();
        testGen.drawWorld();

//        Position testPos = new Position(25, 25);
//        Position testPos2 = new Position (10, 10);
//        Room testRoom = new Room(testPos, 1, RANDOM);
//        Hallway testHall = new Hallway(testPos2, 3, RANDOM);
//        testRoom.drawSpace(testWorld);
//        testHall.drawSpace(testWorld);

        test.renderFrame(testWorld);
    }
}

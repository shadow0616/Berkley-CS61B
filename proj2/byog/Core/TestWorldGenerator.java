package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.RandomWorldDemo;
import java.util.Random;

public class TestWorldGenerator {
    private static final int HEIGHT = 30;
    private static final int WIDTH = 80;
//    private static final long SEED = 3; // 2873123




    public static void main(String[] args) {
        TERenderer test = new TERenderer();
        test.initialize(WIDTH, HEIGHT);
        TETile[][] testWorld = new TETile[WIDTH][HEIGHT];
        RandomWorldDemo.fillWithNothing(testWorld);
        Random random = new Random(WorldGenerator.parseInput("N123123123164756S"));
        WorldGenerator testGen = new WorldGenerator(testWorld, random);

        int width = testWorld.length;
        int height = testWorld[0].length;
        System.out.println("width: " + width + ", height: " + height);
        testGen.generateWorld();

//        for (Space space: testGen.spaces) {
//            System.out.println(space.inBoundCheck(width, height));
//            System.out.println("left top: "
//                + "(" + space.corners[1].x + ", " + space.corners[1].y + ")");
//            System.out.println("right bot: "
//                + "(" + space.corners[3].x + ", " + space.corners[3].y + ")");
//            System.out.println("");
//        }

//        System.out.println("(" + testGen.spaces.get(0).entrance.x + ", " +
//                testGen.spaces.get(0).entrance.y + ")");
//        testGen.drawWorld();

//        Position testPos = new Position(25, 25);
//        Position testPos2 = new Position (10, 10);
//        Room testRoom = new Room(testPos, 1, RANDOM);
//        Hallway testHall = new Hallway(testPos2, 3, RANDOM);
//        testRoom.drawSpace(testWorld);
//        testHall.drawSpace(testWorld);

        test.renderFrame(testWorld);
    }
}

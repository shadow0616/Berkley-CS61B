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
//        testGen.spaces[0].drawSpace(testWorld);
//        testGen.spaces[1].drawSpace(testWorld);
//        testGen.spaces[2].drawSpace(testWorld);
//        testGen.spaces[3].drawSpace(testWorld);
//        testGen.spaces[4].drawSpace(testWorld);
//        System.out.println(testGen.spaces[2].overlapCheck(testGen.spaces[4]));
//        Position leftTop1 = testGen.spaces[2].corners[1];
//        Position rightBot1 = testGen.spaces[2].corners[3];
//        Position leftTop2 = testGen.spaces[4].corners[1];
//        Position rightBot2 = testGen.spaces[4].corners[3];
//        System.out.println("space 2 left top: " + "(" + leftTop1.x + ", " + leftTop1.y + ")");
//        System.out.println("space 2 right bot: " + "(" + rightBot1.x + ", " + rightBot1.y + ")");
//        System.out.println("space 4 left top: " + "(" + leftTop2.x + ", " + leftTop2.y + ")");
//        System.out.println("space 4 right bot: " + "(" + rightBot2.x + ", " + rightBot2.y + ")");
//        Position testPos = new Position(25, 25);
//        Position testPos2 = new Position (10, 10);
//        Room testRoom = new Room(testPos, 1, RANDOM);
//        Hallway testHall = new Hallway(testPos2, 3, RANDOM);


//        testRoom.drawSpace(testWorld);
//        testHall.drawSpace(testWorld);
        test.renderFrame(testWorld);
    }
}

package byog.Core;

import byog.TileEngine.TETile;
import java.util.Random;

public class WorldGenerator {
    public Space[] spaces = new Space[6];
    private Random random;
    private TETile[][] world;
    private int width;
    private int height;


    public WorldGenerator(TETile[][] world, Random r) {
        this.world = world;
        this.random = r;
        this.width = world.length;
        this.height = world[0].length;
    }

    public void branchOut(Space curr, int spaceCount) {
        for (int side = 0; side < 4; side += 1) {
            Position exit = curr.exits[side];
            if (exit != null) {
                Space newSpace;
                int newEntSide = curr.calcEntSide(side);
                Position newEnt = curr.calcNewEnt(exit, side);
                switch (random.nextInt(2)) {
                    case 0:
                        newSpace = new Room(newEnt, newEntSide, random);
                        break;
                    case 1:
                        newSpace = new Hallway(newEnt, newEntSide, random);
                        break;
                    default: throw new RuntimeException("this shouldn't happen!");
                }
                boolean inBound = newSpace.inBoundCheck(width, height);
                boolean overlap = this.overlapCheckSpaces(newSpace);
                if (inBound && !overlap) {
                    spaceCount += 1;
                    spaces[spaceCount - 1] = newSpace;
                }
                if (spaceCount < spaces.length) {
                    branchOut(newSpace, spaceCount);
                } else {
                    return;
                }
            }
        }
    }

    public boolean overlapCheckSpaces(Space newSpace) {
        for (Space space: spaces) {
            if (space != null) {
                if (space.overlapCheck(newSpace)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void generateWorld() {
        int index = 1;
        Position initPos = new Position(50, 30);
        Room initRoom = new Room(initPos, 0, random);
        spaces[0] = initRoom;
        branchOut(initRoom, 1);
    }

    public void drawWorld() {
        for (Space space: spaces) {
            space.drawSpace(world);
        }
    }

}

package byog.Core;

import byog.TileEngine.TETile;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class WorldGenerator {
    public List<Space> spaces = new ArrayList<>();
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

    public void branchOut(Space curr) {
        curr.drawSpace(world);
        spaces.get(0).drawLockedDoor(world);
        for (int side = 0; side < 4; side += 1) {
            Position exit = curr.exits[side];
            int judgeNum = random.nextInt(2);
            if (exit != null && judgeNum == 1) {
                for (int i = 0; i < 3; i += 1) {
                    Space newSpace = randRoomOrHallway(curr, side);
                    boolean inBound = newSpace.inBoundCheck(width, height);
                    boolean overlap = this.overlapCheckSpaces(newSpace);
                    if (inBound && !overlap) {
                        curr.fillExit(world, side);
                        spaces.add(newSpace);
                        branchOut(newSpace);
                        break;
                    }
                }
            }
        }
    }

    public Space randRoomOrHallway(Space curr, int side) {
        Space newSpace;
        Position exit = curr.exits[side];
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
        return newSpace;
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
        Position initPos = new Position(50, 30);
        Room initRoom = new Room(initPos, 0, random);
        spaces.add(initRoom);
        branchOut(initRoom);
    }

    public void drawWorld() {
        for (Space space: spaces) {
            space.drawSpace(world);
        }
    }

}

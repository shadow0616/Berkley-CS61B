package byog.Core;

import java.util.Random;

public class Room extends Space {
    public Room(Position entrance, int entSide, Random r) {
        this.entrance = entrance;
        this.entSide = entSide;
        this.random = r;
        // room size constraint, max: 9 x 9, min: 2 x 2, hard coded for now
        width = random.nextInt(5) + 4;
        height = random.nextInt(5) + 4;
//            width = 9;
//            height = 9;
        // randomizes entrance position in the walls of the room
        int entWallPos = this.randomEntrance();
        // calculate the start position of the room (bottom left corner, including walls)
        this.calcStartPos(entWallPos);
        // assign corners
        this.assignCorners();
        // assign walls
        this.assignWalls();
        // assign floors
        this.assignFloors();
        // create random openings
        this.randomExits();
    }
    public int randomEntrance() {
        int entWallPos;
        switch (entSide % 2) {
            case 0: entWallPos = 1 + random.nextInt(width - 2); break; // avoid corners
            case 1: entWallPos = 1 + random.nextInt(height - 2); break; // avoid corners
            default:
                throw new IllegalStateException("Unexpected value: " + entSide % 2);
        }
        return entWallPos;
    }
    public void calcStartPos(int entWallPos) {
        switch (entSide) {
            case 0:
                startPos = new Position(entrance.x - entWallPos, entrance.y);
                break;
            case 1:
                startPos = new Position(entrance.x, entrance.y  - entWallPos);
                break;
            case 2:
                startPos = new Position(entrance.x - entWallPos, entrance.y  - height + 1);
                break;
            case 3:
                startPos = new Position(entrance.x - width + 1, entrance.y  - entWallPos);
                break;
            default: throw new RuntimeException("unrecognized entrance side number");
        }
    }
}

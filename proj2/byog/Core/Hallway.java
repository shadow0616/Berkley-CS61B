package byog.Core;

import java.util.Random;

public class Hallway extends Space {
    public Hallway(Position entrance, int entSide, Random r) {
        this.entrance = entrance;
        this.entSide = entSide;
        this.random = r;
        this.assignWandH();
        this.calcStartPos();
        this.assignCorners();
        this.assignWalls();
        this.assignFloors();
        this.randomExits();
    }

    public void assignWandH() {
        switch (entSide % 2) {
            case 0:
                width = 3;
                height = random.nextInt(7) + 3;
//                height = 9;
                break;
            case 1:
                height = 3;
                width = random.nextInt(7) + 3;
//                width = 9;
                break;
            default: throw new RuntimeException("unrecognized entrance side number");
        }
    }
    public void calcStartPos() {
        switch (entSide) {
            case 0: startPos = new Position(entrance.x - 1, entrance.y); break;
            case 1: startPos = new Position(entrance.x, entrance.y - 1); break;
            case 2: startPos = new Position(entrance.x - 1, entrance.y - height + 1); break;
            case 3: startPos = new Position(entrance.x - width + 1, entrance.y - 1); break;
            default: throw new RuntimeException("unrecognized entrance side number");
        }
    }
}

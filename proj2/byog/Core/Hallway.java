package byog.Core;

import java.util.Random;

public class Hallway extends Space {
    public Hallway(Position entrance, int entSide, Random RANDOM) {
        this.entrance = entrance;
        this.entSide = entSide;
        this.RANDOM = RANDOM;
        this.assignWandH();
        this.calcStartPos();
        this.assignCorners();
        this.assignWalls();
        this.assignFloors();
        this.randomExits();
    }

    public void assignWandH() {
        switch(entSide % 2) {
            case 0:
                width = 3;
//                    height = RANDOM.nextInt(9) + 1;
                height = 9;
                break;
            case 1:
                height = 3;
//                    width = RANDOM.nextInt(9) + 1;
                width = 9;
                break;
        }
    }
    public void calcStartPos() {
        switch(entSide) {
            case 0: startPos = new Position(entrance.x - 1, entrance.y); break;
            case 1: startPos = new Position(entrance.x, entrance.y - 1); break;
            case 2: startPos = new Position(entrance.x - 1, entrance.y - height + 1); break;
            case 3: startPos = new Position(entrance.x - width + 1, entrance.y - 1);
        }
    }
}

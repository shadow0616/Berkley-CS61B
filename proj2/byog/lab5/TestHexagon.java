package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestHexagon {
//    static RandomWorldDemo testWorld = new RandomWorldDemo();
    @Test
    public void testCalcRowOffSet() {
        assertEquals(1, RandomWorldDemo.calcOffSet(2, 1));
        assertEquals(1, RandomWorldDemo.calcOffSet(4, 3));
        assertEquals(2, RandomWorldDemo.calcOffSet(5, 3));
        assertEquals(4, RandomWorldDemo.calcOffSet(5, 1));
    }

    @Test
    public void testCalcRowLength() {
        assertEquals(4, RandomWorldDemo.calcWidth(2, 2));
        assertEquals(7, RandomWorldDemo.calcWidth(3, 3));
        assertEquals(5, RandomWorldDemo.calcWidth(3, 2));
        assertEquals(11, RandomWorldDemo.calcWidth(5, 4));
        assertEquals(13, RandomWorldDemo.calcWidth(5, 5));
        assertEquals(10, RandomWorldDemo.calcWidth(4, 4));
        assertEquals(4, RandomWorldDemo.calcWidth(4, 1));
    }

}

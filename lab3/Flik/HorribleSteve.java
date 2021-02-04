public class HorribleSteve {
    /** when i & j >= 128, Flik.isSameNumber will return false even if i & j are the
     * same numerically */
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
//        System.out.print(Flik.isSameNumber(120, 120));
    }
}

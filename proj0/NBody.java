public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int numPlanets = in.readInt();
        int i = 0;
        in.readDouble();
        Planet[] Planets = new Planet[numPlanets];
        while (i < numPlanets) {
            Planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
                                    in.readString());
            i += 1;
        }
        return Planets;
    }
    public static void main(String[] args) {
        int waitTimeMilliseconds = 10;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] Planets = NBody.readPlanets(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        double[] xForces = new double[Planets.length];
        double[] yForces = new double[Planets.length];
        double t = 0;
        while (t < T) {
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (int i = 0; i < Planets.length; i += 1) {
                xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
                yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
            }
            for (int i = 0; i < Planets.length; i += 1) {
                Planets[i].update(dt, xForces[i], yForces[i]);
                Planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(waitTimeMilliseconds);
            t += dt;
        }
        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                    Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
        }
    }
}

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet P) {
        this.xxPos = P.xxPos;
        this.yyPos = P.yyPos;
        this.xxVel = P.xxVel;
        this.yyVel = P.yyVel;
        this.mass = P.mass;
        this.imgFileName = P.imgFileName;
    }
    public double calcDistance(Planet P) {
        double xDiff = this.xxPos - P.xxPos;
        double yDiff = this.yyPos - P.yyPos;
        return Math.pow(xDiff * xDiff + yDiff * yDiff, 0.5);
    }
    public double calcForceExertedBy(Planet P) {
        double r = this.calcDistance(P);
        return (Planet.G * this.mass * P.mass) / (r * r);
    }
    public double calcForceExertedByX(Planet P) {
        double xDiff = P.xxPos - this.xxPos;
        double r = this.calcDistance(P);
        double force = calcForceExertedBy(P);
        return force * xDiff / r;
    }
    public double calcForceExertedByY(Planet P) {
        double yDiff = P.yyPos - this.yyPos;
        double r = this.calcDistance(P);
        double force = calcForceExertedBy(P);
        return force * yDiff / r;
    }
    public double calcNetForceExertedByX(Planet[] Ps) {
        double netForceX = 0;
        for (Planet P: Ps) {
            if (this == P) {
                continue;
            }
            netForceX += this.calcForceExertedByX(P);
        }
        return netForceX;
    }
    public double calcNetForceExertedByY(Planet[] Ps) {
        double netForceY = 0;
        for (Planet P: Ps) {
            if (this == P) {
                continue;
            }
            netForceY += this.calcForceExertedByY(P);
        }
        return netForceY;
    }
    public void update(double dt, double fX, double fY) {
        double xxAcc = fX / this.mass;
        double yyAcc = fY / this.mass;
        this.xxVel = this.xxVel + dt * xxAcc;
        this.yyVel = this.yyVel + dt * yyAcc;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + this.imgFileName);
    }
}










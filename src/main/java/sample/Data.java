package sample;

/**
 * Created by Eugene13 on 03.10.2016.
 */

class Data {
    private int n;
    private double[] vectorX;
    private double[] vectorY;
    private double aproxFx;

    int getN() {
        return n;
    }

    double getAproxFx() {
        return this.aproxFx;
    }

    void setAproxFx(double fx) {
        this.aproxFx = fx;
    }

    void setN(int n) {
        this.n = n;
        this.vectorY = new double[n];
        this.vectorX = new double[n];
    }

    double[] getVectorX() {
        return vectorX;
    }

    double[] getVectorY() {
        return vectorY;
    }
}

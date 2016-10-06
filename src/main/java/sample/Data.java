package sample;

/**
 * Created by Eugene13 on 03.10.2016.
 * Class Data:
 * 1) Значения х и y;
 */

class Data {
    private int n;
    private double[] vectorX;
    private double[] vectorY;

    int getN() {
        return n;
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

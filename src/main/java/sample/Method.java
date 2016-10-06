package sample;

/**
 * Created by Eugene13 on 04.10.2016.
 */
class Method {
    void sort(Data data) {
        int countNodes = data.getN();
        double[] vecX = data.getVectorX();
        double[] vecY = data.getVectorY();
        double dopX, dopY;
        for (int i = 0; i < countNodes; i++) {
            for (int j = i; j > 0; j--) {
                if (vecX[j] < vecX[j - 1]) {
                    dopX = vecX[j];
                    dopY = vecY[j];
                    vecX[j] = vecX[j - 1];
                    vecY[j] = vecY[j - 1];
                    vecX[j - 1] = dopX;
                    vecY[j - 1] = dopY;
                }
            }
        }
    }
}


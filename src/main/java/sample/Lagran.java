package sample;

/**
 * Created by Eugene13 on 05.10.2016.
 * Class Lagran:
 * 1) Реализована логика аппроксимации функции методом полинома Лагранжа;
 */

class Lagran extends Method {
    double solution(Data data, double apx) {
        double valueFapx = 0;
        double s;
        int countNodes = data.getN();
        double[] vecX = data.getVectorX();
        double[] vecY = data.getVectorY();
        for (int i = 0; i < countNodes; i++) {
            s = 1;
            for (int j = 0; j < countNodes; j++) {
                if (j != i) {
                    s = s * (apx - vecX[j]) / (vecX[i] - vecX[j]);
                }
            }
            valueFapx = valueFapx + vecY[i] * s;
        }
        return valueFapx;
    }
}

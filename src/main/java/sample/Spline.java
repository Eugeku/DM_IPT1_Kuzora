package sample;

/**
 * Created by Eugene13 on 05.10.2016.
 * Class Spline:
 * 1) Реализована основная логика аппроксимации методом кубических сплайнов;
 * 2) Коэффициенты для каждого сплайна между заранее определенными узлами
 * хранятся в массиве splines.
 */

class Spline extends Method {
    private SplineTuple[] splines;

    void BuildSpline(double[] x, double[] y, int n) {
        splines = new SplineTuple[n];
        for (int i = 0; i < n; i++) {
            splines[i] = new SplineTuple();
        }
        for (int i = 0; i < n; ++i) {
            splines[i].x = x[i];
            splines[i].a = y[i];
        }
        splines[0].c = splines[n - 1].c = 0.0;
        double[] alpha = new double[n - 1];
        double[] beta = new double[n - 1];
        alpha[0] = beta[0] = 0.0;
        for (int i = 1; i < n - 1; ++i) {
            double h_i = x[i] - x[i - 1], h_i1 = x[i + 1] - x[i];
            double A = h_i;
            double C = 2.0 * (h_i + h_i1);
            double B = h_i1;
            double F = 6.0 * ((y[i + 1] - y[i]) / h_i1 - (y[i] - y[i - 1]) / h_i);
            double z = (A * alpha[i - 1] + C);
            alpha[i] = -B / z;
            beta[i] = (F - A * beta[i - 1]) / z;
        }
        for (int i = n - 2; i > 0; --i)
            splines[i].c = alpha[i] * splines[i + 1].c + beta[i];
        beta = null;
        alpha = null;
        for (int i = n - 1; i > 0; --i) {
            double h_i = x[i] - x[i - 1];
            splines[i].d = (splines[i].c - splines[i - 1].c) / h_i;
            splines[i].b = h_i * (2.0 * splines[i].c + splines[i - 1].c) / 6.0 + (y[i] - y[i - 1]) / h_i;
        }
    }

    double f(double x) {
        SplineTuple s;
        int n = splines.length;
        if (x <= splines[0].x)
            s = splines[1];
        else if (x >= splines[n - 1].x)
            s = splines[n - 1];
        else {
            int i = 0, j = n - 1;
            while (i + 1 < j) {
                int k = i + (j - i) / 2;
                if (x <= splines[k].x)
                    j = k;
                else
                    i = k;
            }
            s = splines[j];
        }
        double dx = (x - s.x);
        return s.a + (s.b + (s.c / 2.0 + s.d * dx / 6.0) * dx) * dx;
    }
}

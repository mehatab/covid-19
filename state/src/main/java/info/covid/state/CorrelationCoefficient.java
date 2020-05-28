package info.covid.state;

import android.graphics.Point;

import java.util.List;

import info.covid.data.models.DataPoint;

class CorrelationCoefficient {

    // function that returns correlation coefficient. 
    static float correlationCoefficient(DataPoint Y[], int n) {

        float sum_X = 0, sum_Y = 0, sum_XY = 0;
        float squareSum_X = 0, squareSum_Y = 0;

        for (int i = 0; i < n; i++) {
            // sum of elements of array X.
            sum_X = sum_X + (i + 1);

            // sum of elements of array Y.
            sum_Y = sum_Y + Y[i].getAmount();

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + (i + 1) * Y[i].getAmount();

            // sum of square of array elements.
            squareSum_X = squareSum_X + (i + 1) * (i + 1);
            squareSum_Y = squareSum_Y + Y[i].getAmount() * Y[i].getAmount();
        }

        // use formula for calculating correlation
        // coefficient.
        float corr = (float) (n * sum_XY - sum_X * sum_Y) /
                (float) (Math.sqrt((n * squareSum_X -
                        sum_X * sum_X) * (n * squareSum_Y -
                        sum_Y * sum_Y)));

        return corr;
    }

    public static float computePersonsCorrelationCoefficent(DataPoint[] Y, int n) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;
        double sumY2 = 0;

        for (int i = 0; i < n; i++) {
            float y = Y[i].getAmount();

            sumX +=  i;
            sumY +=  i;
            sumXY +=  i * y;
            sumX2 +=  i *  i;
            sumY2 += y * y;
        }

        final double bottom = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));
        if (bottom == 0)
            return 0;
        final double top = n * sumXY - sumX * sumY;
        return (float) (top / bottom);
    }




    static double computeCorrelation(List<Point> points) {
        double n = points.size();
        Point sums = sum(points);
        double sumxy = prodsum(points);
        Point sqs = squaresum(points);

        double stdx = Math.sqrt(n * sqs.x - sums.x * sums.x);
        double stdy = Math.sqrt(n * sqs.y - sums.y * sums.y);
        double cov = n * sumxy - sums.x * sums.y;
        return cov / (stdx * stdy);
    }


    static Point sum(List<Point> points) {
        Point sum = new Point(0, 0);
        for (Point p: points) {
            sum.x += p.x;
            sum.y += p.y;
        }
        return sum;
    }

    static Point squaresum(List<Point> points) {
        Point sum = new Point(0, 0);
        for (Point p: points) {
            sum.x += p.x * p.x;
            sum.y += p.y * p.y;
        }
        return sum;
    }

    static double prodsum(List<Point> points) {
        double sum = 0;
        for (Point p: points) {
            sum += p.x * p.y;
        }
        return sum;
    }

}
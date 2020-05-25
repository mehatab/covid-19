package info.covid.state;

import info.covid.data.models.DataPoint;

class GFG {

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
}
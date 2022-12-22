package edna.cipher.impls.rijndael.util.operations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;

import java.util.Arrays;

import static java.lang.Math.abs;

public class RijndaelMathOperations {

    // Рэйнделловское умножение матрицы на вектор
    public static int[] multMatrixOnVector(int[][] matrix, int[] vector) {
        int[] res = new int[vector.length];

        for (int i = 0; i < res.length; i++) {
            res[i] = multVectors(matrix[i], vector);
        }

        return res;
    }

    // Рэйнделловское умножение векторов
    public static int multVectors(int[] a, int[] b) {
        int currentSum = 0;

        for (int i = 0; i < a.length; i++) {
            int res = byteMult(a[i], b[i]);
            currentSum = byteSum(currentSum, res);
        }

        return currentSum;
    }

    // Рэйнделловское умножение (перевести числа в полиномы + умножить как полиномы +
    // привести к Рэйнделловской форме + заменить на остаток от деления Rijndael Prime Polynomial)
    public static int byteMult(int a, int b) {
        int[] aBits = RijndaelLowLevelOperations.intToBitsArray(a);
        int[] bBits = RijndaelLowLevelOperations.intToBitsArray(b);
        int[] result = new int[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN];
        int m = powerOfPolynomial(aBits);
        int n = powerOfPolynomial(bBits);

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                result[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - m - n - 1 + i + j]
                        += aBits[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - m - 1 + i]
                        * bBits[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - n - 1 + j];
            }
        }

        byteSumForAllBits(result);
        return byteModRijndaelPolynomial(RijndaelLowLevelOperations.bitsArrayToInt(result));
    }

    // Найти степень полиномиального (в определениях алгоритма Rijndael) представления массива байтов
    // (10 -> [0, ..., 0, 1, 0, 1, 0] -> x^3 + x^1 -> powerOfPolynomial() == 3)
    public static int powerOfPolynomial(int[] a) {
        if (RijndaelLowLevelOperations.bitsArrayToInt(a) == 0) {
            return 0;
        }
        int pos = 0;
        while (a[pos] == 0) {
            pos++;
        }

        return RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - pos - 1;
    }

    // Приведение полинома к Рэйнделовской форме
    // (x^4 + 2*x^3 + 5*x^2 + x^1 - 2x^0 -> x^4 + x^2 + x^1, т.к. сумма
    // считается как XOR)
    public static void byteSumForAllBits(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int resultI = 0;

            for (int j = 0; j < abs(a[i]); j++) {
                resultI = byteSum(resultI, (byte) 1);
            }

            a[i] = resultI;
        }
    }

    // Найти остаток от деления числа на простой полином Рэйнделла
    private static int byteModRijndaelPolynomial(int dividend) {
        int[] dividendBits = RijndaelLowLevelOperations.intToBitsArray(dividend);
        int[] divisorBits = RijndaelLowLevelOperations.intToBitsArray(RijndaelConsts.PRIME_POLYNOMIAL);
        int[] result = new int[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN];
        int m = powerOfPolynomial(dividendBits);
        int n = powerOfPolynomial(divisorBits);
        int resIt = RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - m + n - 1;

        for (int i = RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - m - 1; i < RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN; i++) {
            if (powerOfPolynomial(dividendBits) < powerOfPolynomial(divisorBits)) {
                continue;
            }
            result[resIt] = dividendBits[i] / divisorBits[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - n - 1];
            int delta = 0;
            for (int j = RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - n - 1; j < RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN; j++, delta++) {
                dividendBits[i + delta] -= result[resIt] * divisorBits[j];
            }
            resIt++;
        }

        byteSumForAllBits(dividendBits);
        return RijndaelLowLevelOperations.bitsArrayToInt(dividendBits);
    }

    // XOR двух чисел (определение суммы в рамках алгоритма Rijndael)
    public static int byteSum(int a, int b) {
        return a ^ b;
    }

    // Рэйнделловское суммирование матриц
    public static int[][] byteSumMatrices(int[][] a, int[][] b) {
        int[][] aCopy = Arrays.copyOf(a, a.length);

        for (int i = 0; i < aCopy.length; i++) {
            for (int j = 0; j < aCopy[0].length; j++) {
                aCopy[i][j] = byteSum(aCopy[i][j], b[i][j]);
            }
        }

        return aCopy;
    }

    // Рэйнделловское суммирование векторов
    public static int[] byteSumVectors(int[] a, int[] b) {
        int[] aCopy = Arrays.copyOf(a, a.length);
        for (int i = 0; i < aCopy.length; i++) {
            aCopy[i] = byteSum(aCopy[i], b[i]);
        }

        return aCopy;
    }
}

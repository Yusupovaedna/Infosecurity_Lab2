package edna.cipher.impls.rijndael.util.operations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;

import java.util.Arrays;

public class RijndaelSpecificOperations {

    public static int[] getColumn(int[][] array, int a) {
        int[] col = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            col[i] = array[i][a];
        }

        return col;
    }

    public static void setColumn(int[][] array, int[] col, int a) {
        for (int i = 0; i < array.length; i++) {
            array[i][a] = col[i];
        }
    }

    public static void shiftRowLeft(int[] row, int times) {
        for (int i = 0; i < times; i++) {
            int temp = row[0];

            for (int j = 0; j < row.length - 1; j++) {
                row[j] = row[j + 1];
            }

            row[row.length - 1] = temp;
        }
    }

    public static void shiftRowRight(int[] row, int times) {
        for (int i = 0; i < times; i++) {
            int temp = row[row.length - 1];

            for (int j = row.length - 1; j > 0; j--) {
                row[j] = row[j - 1];
            }

            row[0] = temp;
        }
    }

    public static int[][] subBytesMatrix(int[][] matrix) {
        int[][] matrixCopy = Arrays.copyOf(matrix, matrix.length);

        for (int i = 0; i < matrixCopy.length; i++) {
            matrixCopy[i] = subBytesVector(matrixCopy[i]);
        }

        return matrixCopy;
    }

    public static int[] subBytesVector(int[] vec) {
        int[] vecCopy = Arrays.copyOf(vec, vec.length);

        for (int i = 0; i < vecCopy.length; i++) {
            int[] hexIJ = new int[2];
            hexIJ[0] = vecCopy[i] / RijndaelConsts.SBOX_WIDTH;
            hexIJ[1] = vecCopy[i] % RijndaelConsts.SBOX_WIDTH;
            vecCopy[i] = RijndaelConsts.SBOX[hexIJ[0]][hexIJ[1]];
        }

        return vecCopy;
    }

    public static int[][] invSubBytesMatrix(int[][] matrix) {
        int[][] matrixCopy = Arrays.copyOf(matrix, matrix.length);

        for (int i = 0; i < matrixCopy.length; i++) {
            matrixCopy[i] = invSubBytesVector(matrixCopy[i]);
        }

        return matrixCopy;
    }

    public static int[] invSubBytesVector(int[] vec) {
        int[] vecCopy = Arrays.copyOf(vec, vec.length);

        for (int i = 0; i < vecCopy.length; i++) {
            int[] hexIJ = new int[2];
            hexIJ[0] = vecCopy[i] / RijndaelConsts.SBOX_WIDTH;
            hexIJ[1] = vecCopy[i] % RijndaelConsts.SBOX_WIDTH;
            vecCopy[i] = RijndaelConsts.INV_SBOX[hexIJ[0]][hexIJ[1]];
        }

        return vecCopy;
    }


}
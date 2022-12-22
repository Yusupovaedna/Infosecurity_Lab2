package edna.cipher.impls.rijndael.transformations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;
import edna.cipher.impls.rijndael.util.operations.RijndaelMathOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelSpecificOperations;
import edna.cipher.utils.io.BlockLogger;

public class RijndaelTransformations {

    public static int[][] addRoundKey(int[][] state, int[][] roundKey, BlockLogger logger) {
        state = RijndaelMathOperations.byteSumMatrices(state, roundKey);

        logger.println("AddRoundKey: ");
        logger.printBlockHex(state);

        return state;
    }

    public static int[][] subBytes(int[][] state, BlockLogger logger) {
        state = RijndaelSpecificOperations.subBytesMatrix(state);

        logger.println("SubBytes: ");
        logger.printBlockHex(state);

        return state;
    }

    public static void shiftRows(int[][] state, BlockLogger logger) {
        RijndaelSpecificOperations.shiftRowLeft(state[1], 1);
        RijndaelSpecificOperations.shiftRowLeft(state[2], 2);
        RijndaelSpecificOperations.shiftRowLeft(state[3], 3);

        logger.println("ShiftRows: ");
        logger.printBlockHex(state);
    }

    public static void mixColumns(int[][] state, BlockLogger logger) {
        for (int i = 0; i < state.length; i++) {
            int[] col = RijndaelSpecificOperations.getColumn(state, i);
            int[] newCol = RijndaelMathOperations.multMatrixOnVector(RijndaelConsts.MIX_COLUMNS_MATRIX, col);
            RijndaelSpecificOperations.setColumn(state, newCol, i);
        }

        logger.println("MixColumns: ");
        logger.printBlockHex(state);
    }
}
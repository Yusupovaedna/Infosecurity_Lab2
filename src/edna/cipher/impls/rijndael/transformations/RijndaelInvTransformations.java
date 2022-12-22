package edna.cipher.impls.rijndael.transformations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;
import edna.cipher.impls.rijndael.util.operations.RijndaelMathOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelSpecificOperations;
import edna.cipher.utils.io.BlockLogger;

public class RijndaelInvTransformations {

    public static int[][] addRoundKey(int[][] state, int[][] roundKey, BlockLogger logger) {
        state = RijndaelMathOperations.byteSumMatrices(state, roundKey);

        logger.println("InvAddRoundKey: ");
        logger.printBlockHex(state);

        return state;
    }

    public static int[][] subBytes(int[][] state, BlockLogger logger) {
        state = RijndaelSpecificOperations.invSubBytesMatrix(state);

        logger.println("InvSubBytes: ");
        logger.printBlockHex(state);

        return state;
    }

    public static void shiftRows(int[][] state, BlockLogger logger) {
        RijndaelSpecificOperations.shiftRowRight(state[1], 1);
        RijndaelSpecificOperations.shiftRowRight(state[2], 2);
        RijndaelSpecificOperations.shiftRowRight(state[3], 3);

        logger.println("invShiftRows: ");
        logger.printBlockHex(state);
    }

    public static void mixColumns(int[][] state, BlockLogger logger) {
        for (int i = 0; i < state.length; i++) {
            int[] col = RijndaelSpecificOperations.getColumn(state, i);
            int[] newCol = RijndaelMathOperations.multMatrixOnVector(RijndaelConsts.INV_MIX_COLUMNS_MATRIX, col);
            RijndaelSpecificOperations.setColumn(state, newCol, i);
        }

        logger.println("InvMixColumns: ");
        logger.printBlockHex(state);
    }
}
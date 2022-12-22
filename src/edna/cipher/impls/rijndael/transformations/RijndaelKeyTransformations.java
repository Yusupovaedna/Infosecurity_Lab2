package edna.cipher.impls.rijndael.transformations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;
import edna.cipher.impls.rijndael.util.operations.RijndaelDataBlocksOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelMathOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelSpecificOperations;

import java.util.Arrays;

public class RijndaelKeyTransformations {

    public static int[][] getKeyFromString(String strKey) {
        int[] symbols = strKey.chars().toArray();
        return RijndaelDataBlocksOperations.splitIntoBlocks(symbols)[0];
    }

    public static int[] getRconFunc(int[] col, int[] rconCol, int[] additionalCol) {
        int[] colCopy = Arrays.copyOf(col, col.length);
        RijndaelSpecificOperations.shiftRowLeft(colCopy, 1);

        colCopy = RijndaelSpecificOperations.subBytesVector(colCopy);
        colCopy = RijndaelMathOperations.byteSumVectors(colCopy, rconCol);
        colCopy = RijndaelMathOperations.byteSumVectors(colCopy, additionalCol);

        return colCopy;
    }

    public static int[][] getExpandedBlock(int[][] prevBlock, int roundNumber) {
        int[][] block = new int[4][4];

        RijndaelSpecificOperations.setColumn(block,
                getRconFunc(RijndaelSpecificOperations.getColumn(prevBlock, 3),
                        RijndaelConsts.RCON[roundNumber],
                        RijndaelSpecificOperations.getColumn(prevBlock, 0)),
                0);

        for (int i = 1; i < block.length; i++) {
            RijndaelSpecificOperations.setColumn(block,
                    RijndaelMathOperations.byteSumVectors(RijndaelSpecificOperations.getColumn(prevBlock, i),
                            RijndaelSpecificOperations.getColumn(block, i - 1)), i);
        }

        return block;
    }

    public static int[][][] getKeyExpansion(int[][] state) {
        int[][][] keys = new int[11][state.length][state.length];

        keys[0] = Arrays.copyOf(state, state.length);

        for (int i = 1; i < 11; i++) {
            keys[i] = getExpandedBlock(keys[i - 1], i);
        }

        return keys;
    }
}
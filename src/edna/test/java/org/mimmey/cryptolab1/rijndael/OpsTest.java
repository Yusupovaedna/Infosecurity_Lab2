package edna.test.java.org.mimmey.cryptolab1.rijndael;

import org.junit.jupiter.api.Test;
import edna.cipher.impls.rijndael.util.operations.RijndaelLowLevelOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelMathOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelSpecificOperations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpsTest {

    @Test
    public void checkIntToBitsArray() {
        int a = 0b01010111;

        assertArrayEquals(new int[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1
        }, RijndaelLowLevelOperations.intToBitsArray(a));
    }

    @Test
    public void checkBitsArrayToInt() {
        int[] arg = new int[]{
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 1, 0, 1, 1, 1
        };

        assertEquals(0b01010111, RijndaelLowLevelOperations.bitsArrayToInt(arg));
    }

    @Test
    public void checkByteSum() {
        int a = 0b01010111;
        int b = 0b10000011;

        int e = 0b11010100;

        assertEquals(e, RijndaelMathOperations.byteSum(a, b));
    }

    @Test
    public void checkByteSumForAllBits() {
        int[] arg = new int[]{0, 1, -5, 2, 4, -8};
        int[] e = new int[]{0, 1, 1, 0, 0, 0};

        RijndaelMathOperations.byteSumForAllBits(arg);

        assertArrayEquals(e, arg);
    }

    @Test
    public void checkByteSumVectors() {
        int[] a = new int[]{0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0};
        int[] b = new int[]{1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0};

        int[] e = new int[]{1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0};

        assertArrayEquals(e, RijndaelMathOperations.byteSumVectors(a, b));
    }

    @Test
    public void checkByteSumMatrices() {
        int[][] a = new int[][]{
                {0, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 1, 0}
        };
        int[][] b = new int[][]{
                {1, 1, 1, 0},
                {0, 0, 1, 0},
                {0, 1, 1, 1},
                {0, 0, 1, 0}
        };

        int[][] e = new int[][]{
                {1, 0, 1, 0},
                {1, 1, 1, 0},
                {1, 0, 1, 1},
                {0, 0, 0, 0}
        };

        assertArrayEquals(e, RijndaelMathOperations.byteSumMatrices(a, b));
    }

    @Test
    public void checkByteMult() {
        int a = 0b01010111;
        int b = 0b10000011;

        int e = 0b11000001;

        assertEquals(e, RijndaelMathOperations.byteMult(a, b));
    }

    @Test
    public void checkMultVectors() {
        int[] a = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0b0100, 0b0110, 0b1111, 0b1001};

        int[] b = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0b0101, 0b1100, 0b1101, 0b0011};

        assertEquals(0b1101100, RijndaelMathOperations.multVectors(a, b));
    }

    @Test
    public void checkGetColumn() {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int col = 1;
        int[] expected = new int[]{2, 5, 8};

        assertArrayEquals(expected, RijndaelSpecificOperations.getColumn(array, col));
    }

    @Test
    public void checkSetColumn() {
        int[][] array = new int[][]{{1, 0, 3}, {4, 0, 6}, {7, 0, 9}};
        int colNum = 1;
        int[] col = new int[]{2, 5, 8};

        int[][] expected = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        RijndaelSpecificOperations.setColumn(array, col, colNum);
        assertArrayEquals(expected, array);
    }

    @Test
    public void checkShiftRowLeft() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6};
        int shift = 2;

        int[] expected = new int[]{3, 4, 5, 6, 1, 2};

        RijndaelSpecificOperations.shiftRowLeft(array, shift);
        assertArrayEquals(expected, array);
    }

    @Test
    public void checkShiftRowRight() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6};
        int shift = 2;

        int[] expected = new int[]{5, 6, 1, 2, 3, 4};

        RijndaelSpecificOperations.shiftRowRight(array, shift);
        assertArrayEquals(expected, array);
    }

    @Test
    public void checkSubBytesVector() {
        int[] array = new int[]{
                0x19, 0xa0, 0x9a, 0xe9,
                0x3d, 0xf4, 0xc6, 0xf8,
                0xe3, 0xe2, 0x8d, 0x48,
                0xbe, 0x2b, 0x2a, 0x08
        };

        int[] expected = new int[]{
                0xd4, 0xe0, 0xb8, 0x1e,
                0x27, 0xbf, 0xb4, 0x41,
                0x11, 0x98, 0x5d, 0x52,
                0xae, 0xf1, 0xe5, 0x30
        };

        assertArrayEquals(expected, RijndaelSpecificOperations.subBytesVector(array));
    }
}

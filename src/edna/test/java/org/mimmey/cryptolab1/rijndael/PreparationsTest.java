package edna.test.java.org.mimmey.cryptolab1.rijndael;

import org.junit.jupiter.api.Test;
import edna.cipher.impls.rijndael.util.operations.RijndaelDataBlocksOperations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PreparationsTest {

    @Test
    public void checkSplitIntoBlocks() {
        int[] array = new int[]{
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16,

                17, 18, 19, 20,
                21, 22, 23, 24,
                25, 26, 27, 28,
                29, 30
        };

        int[][][] expected = new int[][][]{
                {
                        {1, 5, 9, 13},
                        {2, 6, 10, 14},
                        {3, 7, 11, 15},
                        {4, 8, 12, 16}
                },
                {
                        {17, 21, 25, 29},
                        {18, 22, 26, 30},
                        {19, 23, 27, 0},
                        {20, 24, 28, 0}
                }
        };

        assertArrayEquals(expected, RijndaelDataBlocksOperations.splitIntoBlocks(array));
    }

    @Test
    public void checkUniteBlocks() {
        int[][][] blocks = new int[][][]{
                {
                        {1, 5, 9, 13},
                        {2, 6, 10, 14},
                        {3, 7, 11, 15},
                        {4, 8, 12, 16}
                },
                {
                        {17, 21, 25, 29},
                        {18, 22, 26, 30},
                        {19, 23, 27, 0},
                        {20, 24, 28, 0}
                }
        };

        int[] expected = new int[]{
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16,

                17, 18, 19, 20,
                21, 22, 23, 24,
                25, 26, 27, 28,
                29, 30, 0, 0
        };

        assertArrayEquals(expected, RijndaelDataBlocksOperations.uniteBlocks(blocks));
    }
}

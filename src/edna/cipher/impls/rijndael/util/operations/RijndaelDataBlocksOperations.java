package edna.cipher.impls.rijndael.util.operations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Locale.IsoCountryCode.*;

public class RijndaelDataBlocksOperations {

    public static int[][][] getBlocksFromString(String text) throws UnsupportedEncodingException {
//        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        byte[] bytes = text.getBytes(Charset.defaultCharset());

        return splitIntoBlocks(RijndaelLowLevelOperations.byteArrayToIntArray(bytes));
    }

    public static String getStringFromBlocks(int[][][] blocks) {
        int[] compressedBlocks = uniteBlocks(blocks);
        byte[] bytes = RijndaelLowLevelOperations.intArrayToByteArray(compressedBlocks);


//        return new String(bytes, StandardCharsets.ISO_8859_1).trim();
        return new String(bytes, Charset.defaultCharset()).trim();
    }

    public static int[][][] splitIntoBlocks(int[] symbols) {
        int addedLength = (RijndaelConsts.BLOCK_CAPACITY - symbols.length % RijndaelConsts.BLOCK_CAPACITY) % RijndaelConsts.BLOCK_CAPACITY;
        int[] symbolsExpansioned = Arrays.copyOf(symbols, symbols.length + addedLength);
        int[][] splittedInto16 = new int[symbolsExpansioned.length / RijndaelConsts.BLOCK_CAPACITY][RijndaelConsts.BLOCK_CAPACITY];

        for (int i = 0; i < symbolsExpansioned.length; i++) {
            splittedInto16[i / RijndaelConsts.BLOCK_CAPACITY][i % RijndaelConsts.BLOCK_CAPACITY] = symbolsExpansioned[i];
        }

        int[][][] res = new int[splittedInto16.length][RijndaelConsts.BLOCK_WIDTH][RijndaelConsts.BLOCK_WIDTH];

        for (int i = 0; i < splittedInto16.length; i++) {
            for (int j = 0; j < splittedInto16[i].length; j++) {
                res[i][j % RijndaelConsts.BLOCK_WIDTH][j / RijndaelConsts.BLOCK_WIDTH] = splittedInto16[i][j];
            }
        }

        return res;
    }

    public static int[] uniteBlocks(int[][][] blocks) {
        int[] res = new int[RijndaelConsts.BLOCK_CAPACITY * blocks.length];
        int resIt = 0;

        for (int[][] block : blocks) {
            for (int j = 0; j < block.length; j++) {
                for (int k = 0; k < block[j].length; k++) {
                    res[resIt] = block[k][j];
                    resIt++;
                }
            }
        }

        return res;
    }
}
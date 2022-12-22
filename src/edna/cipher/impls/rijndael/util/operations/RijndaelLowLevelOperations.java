package edna.cipher.impls.rijndael.util.operations;

import edna.cipher.impls.rijndael.consts.RijndaelConsts;

import static java.lang.Math.abs;

public class RijndaelLowLevelOperations {

    // Перевести число в массив битов (10 -> [0, ... 0, 1, 0, 1, 0])
    public static int[] intToBitsArray(int value) {
        int[] bits = new int[RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN];
        int twoPower = 1 << (RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - 1);
        int i = 0;

        do {
            bits[i] = value / twoPower;
            value %= twoPower;
            twoPower >>= 1;
            i++;
        } while (twoPower > 0);

        return bits;
    }

    // Перевести массив битов в число ([0, ... 0, 1, 0, 1, 0] -> 10)
    public static int bitsArrayToInt(int[] bits) {
        int result = 0;
        int twoPower = 1 << (RijndaelConsts.BITS_PER_NUMBER_WITH_MARGIN - 1);

        for (int bit : bits) {
            result += abs(twoPower * bit);
            twoPower >>= 1;
        }

        return result;
    }

    public static int[] byteArrayToIntArray(byte[] bytes) {
        int[] symbols = new int[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            symbols[i] = bytes[i] & 0xFF;
        }

        return symbols;
    }

    public static byte[] intArrayToByteArray(int[] ints) {
        byte[] bytes = new byte[ints.length];

        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }

        return bytes;
    }

    public static int[][][] reverseArray(int[][][] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int[][] temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        return array;
    }
}

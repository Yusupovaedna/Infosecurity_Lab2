package edna.cipher.impls.rijndael;

import edna.cipher.impls.rijndael.cipherkey.RijndaelKey;
import edna.cipher.impls.rijndael.consts.RijndaelConsts;
import edna.cipher.impls.rijndael.transformations.RijndaelInvTransformations;
import edna.cipher.impls.rijndael.transformations.RijndaelKeyTransformations;
import edna.cipher.impls.rijndael.transformations.RijndaelTransformations;
import edna.cipher.impls.rijndael.util.operations.RijndaelLowLevelOperations;
import edna.cipher.interfaces.Cipher;
import edna.cipher.utils.io.BlockLogger;


public abstract class RijndaelCipherMode implements Cipher<String, String, RijndaelKey, RijndaelKey> {

    protected BlockLogger encodeBlockLogger;
    protected BlockLogger decodeBlockLogger;

    protected int[][] iv;
    protected int[][] sBox;
    protected int[][] invSBox;

    public RijndaelCipherMode() {
        this.sBox = RijndaelConsts.SBOX;
        this.invSBox = RijndaelConsts.INV_SBOX;
        this.iv = RijndaelConsts.IV;
    }

    public final int[][] getEncodedBlock(int[][] block, int[][] key) {
        int[][][] keySchedule = RijndaelKeyTransformations.getKeyExpansion(key);

        encodeBlockLogger.println("NEW BLOCK");
        encodeBlockLogger.println("KeySchedule: ");

        for (int[][] ints : keySchedule) {
            encodeBlockLogger.printBlockHex(ints);
        }

        RijndaelTransformations.addRoundKey(block, keySchedule[0], encodeBlockLogger);

        for (int i = 1; i < 10; i++) {
            block = RijndaelTransformations.subBytes(block, encodeBlockLogger);
            RijndaelTransformations.shiftRows(block, encodeBlockLogger);
            RijndaelTransformations.mixColumns(block, encodeBlockLogger);
            block = RijndaelTransformations.addRoundKey(block, keySchedule[i], encodeBlockLogger);
        }

        block = RijndaelTransformations.subBytes(block, encodeBlockLogger);
        RijndaelTransformations.shiftRows(block, encodeBlockLogger);
        block = RijndaelTransformations.addRoundKey(block, keySchedule[10], encodeBlockLogger);

        return block;
    }

    public final int[][] getDecodedBlock(int[][] block, int[][] key) {
        int[][][] keySchedule = RijndaelKeyTransformations.getKeyExpansion(key);

        decodeBlockLogger.println("NEW BLOCK");
        decodeBlockLogger.println("KeySchedule: ");

        for (int[][] ints : keySchedule) {
            decodeBlockLogger.printBlockHex(ints);
        }

        int[][][] reverseKeySchedule = RijndaelLowLevelOperations.reverseArray(keySchedule);

        decodeBlockLogger.println("InvKeySchedule: ");

        for (int[][] ints : reverseKeySchedule) {
            decodeBlockLogger.printBlockHex(ints);
        }

        block = RijndaelInvTransformations.addRoundKey(block, reverseKeySchedule[0], decodeBlockLogger);
        RijndaelInvTransformations.shiftRows(block, decodeBlockLogger);
        block = RijndaelInvTransformations.subBytes(block, decodeBlockLogger);

        for (int i = 1; i < 10; i++) {
            block = RijndaelInvTransformations.addRoundKey(block, reverseKeySchedule[i], decodeBlockLogger);
            RijndaelInvTransformations.mixColumns(block, decodeBlockLogger);
            RijndaelInvTransformations.shiftRows(block, decodeBlockLogger);
            block = RijndaelInvTransformations.subBytes(block, decodeBlockLogger);
        }

        block = RijndaelInvTransformations.addRoundKey(block, reverseKeySchedule[10], decodeBlockLogger);

        return block;
    }
}

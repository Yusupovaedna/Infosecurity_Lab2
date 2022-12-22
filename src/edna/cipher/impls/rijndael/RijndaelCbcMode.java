package edna.cipher.impls.rijndael;

import edna.cipher.impls.rijndael.cipherkey.RijndaelKey;
import edna.cipher.impls.rijndael.paths.RijndaelPaths;
import edna.cipher.impls.rijndael.transformations.RijndaelKeyTransformations;
import edna.cipher.impls.rijndael.util.operations.RijndaelDataBlocksOperations;
import edna.cipher.impls.rijndael.util.operations.RijndaelMathOperations;
import edna.cipher.utils.io.BlockLogger;

import java.io.UnsupportedEncodingException;

public class RijndaelCbcMode extends RijndaelCipherMode {

    public RijndaelCbcMode() {
        super();
        encodeBlockLogger = new BlockLogger(RijndaelPaths.CBC_ENCODE_LOG.getPath());
        decodeBlockLogger = new BlockLogger(RijndaelPaths.CBC_DECODE_LOG.getPath());
    }

    @Override
    public String encode(String text, RijndaelKey cipherKey) throws UnsupportedEncodingException {
        int[][] key = RijndaelKeyTransformations.getKeyFromString(cipherKey.getKey());
        int[][][] dataBlocks = RijndaelDataBlocksOperations.getBlocksFromString(text);
        int[][] needsCipher = iv;

        for (int i = 0; i < dataBlocks.length; i++) {
            encodeBlockLogger.println("Input block: ");
            encodeBlockLogger.printBlockHex(dataBlocks[i]);

            dataBlocks[i] = RijndaelMathOperations.byteSumMatrices(dataBlocks[i], needsCipher);
            dataBlocks[i] = getEncodedBlock(dataBlocks[i], key);
            needsCipher = dataBlocks[i];

            encodeBlockLogger.println("Output block: ");
            encodeBlockLogger.printBlockHex(dataBlocks[i]);
        }

        return RijndaelDataBlocksOperations.getStringFromBlocks(dataBlocks);
    }

    @Override
    public String decode(String text, RijndaelKey cipherKey) throws UnsupportedEncodingException {
        int[][] key = RijndaelKeyTransformations.getKeyFromString(cipherKey.getKey());
        int[][][] dataBlocks = RijndaelDataBlocksOperations.getBlocksFromString(text);
        int[][] needsEncipher = iv;

        for (int i = 0; i < dataBlocks.length; i++) {
            decodeBlockLogger.println("Input block: ");
            decodeBlockLogger.printBlockHex(dataBlocks[i]);

            int[][] curNeedsEncipher = needsEncipher.clone();
            needsEncipher = dataBlocks[i];
            dataBlocks[i] = getDecodedBlock(dataBlocks[i], key);
            dataBlocks[i] = RijndaelMathOperations.byteSumMatrices(dataBlocks[i], curNeedsEncipher);

            decodeBlockLogger.println("Output block: ");
            decodeBlockLogger.printBlockHex(dataBlocks[i]);
        }

        return RijndaelDataBlocksOperations.getStringFromBlocks(dataBlocks);
    }
}

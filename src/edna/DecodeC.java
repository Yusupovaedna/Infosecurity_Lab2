package edna;

import edna.cipher.impls.rijndael.RijndaelCbcMode;
import edna.cipher.impls.rijndael.RijndaelCipherMode;
import edna.cipher.impls.rijndael.cipherkey.RijndaelKey;
import edna.cipher.impls.rijndael.paths.RijndaelPaths;
import edna.cipher.utils.io.ResourceReader;
import edna.cipher.utils.io.ResourceWriter;

import java.io.UnsupportedEncodingException;

public class DecodeC {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // Принимаем на вход имена входного и выходного файла,
        // ключевое слово
        String key = args.length > 1 ? args[0] : "Secret";

        String inputPath = args.length > 1 ? args[1] : RijndaelPaths.CBC_DECODE_INPUT.getPath();
        String outputPath = args.length > 2 ? args[2] : RijndaelPaths.CBC_DECODE_OUTPUT.getPath();


        RijndaelCipherMode cipher = new RijndaelCbcMode();
        String text = ResourceReader.readFromFile(inputPath);
        ResourceWriter.writeToFile(cipher.decode(text, new RijndaelKey(key)), outputPath);
    }
}

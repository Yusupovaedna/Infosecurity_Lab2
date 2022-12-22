package edna;

import edna.cipher.impls.rijndael.RijndaelCbcMode;
import edna.cipher.impls.rijndael.RijndaelCipherMode;
import edna.cipher.impls.rijndael.cipherkey.RijndaelKey;
import edna.cipher.impls.rijndael.paths.RijndaelPaths;
import edna.cipher.utils.io.ResourceReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EncodeC {
    public static void main(String[] args) throws IOException {
        // Принимаем на вход имена входного и выходного файла,
        // ключевое слово
//        System.out.println("Type your key, please");
//        Scanner scan = new Scanner(System.in);
        String key = args.length > 0 ? args[0] : "Secret";
//        String key = scan.nextLine();

        String inputPath = args.length > 1 ? args[1] : RijndaelPaths.CBC_ENCODE_INPUT.getPath();
        String outputPath = args.length > 2 ? args[2] : RijndaelPaths.CBC_ENCODE_OUTPUT.getPath();


        RijndaelCipherMode cipher = new RijndaelCbcMode();
        String text = ResourceReader.readFromFile(inputPath);
        int k = 1;
        if (text.length() > 16) {
            for (int i = 0; i <= text.length() - 16; i += 16) {
                String input = text.substring(i, i + 16);
                System.out.println("Input block #" + k + " : " + input);
                String encoded = cipher.encode(input, new RijndaelKey(key));
                System.out.println("Encoded block:   " + encoded);
                String decoded = cipher.decode(encoded, new RijndaelKey(key));
                System.out.println("Decoded block:   " + decoded + "\n");
                k++;
//                ResourceWriter.writeToFile(cipher.encode(text.substring(i, i + 16), new RijndaelKey(key)), outputPath);
            }

        }
        if (text.length()%16>0) {
//            System.setProperty(Charset.,"ISO-8859-5");
            String input = text.substring(16*(k-1));
            System.out.println("Input block #" + k + " : " + input);
            String encoded = cipher.encode(input, new RijndaelKey(key));
            System.out.printf("Encoded block:   "+ encoded +"\n", Charset.defaultCharset());
            String decoded = cipher.decode(encoded, new RijndaelKey(key));
            System.out.println("Decoded block:   "+ decoded +"\n");
        }


//        ResourceWriter.writeToFile(cipher.encode(text, new RijndaelKey(key)), outputPath);
    }
}

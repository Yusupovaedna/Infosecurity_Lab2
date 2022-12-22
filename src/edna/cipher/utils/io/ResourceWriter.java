package edna.cipher.utils.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

// Класс, записывающий текст в файл
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceWriter {

    public static void writeToFile(String text, String path) {
//        if(Path.of(path).toFile().delete()){
//            try {
//                Path.of(path).toFile().createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else{
//            //throw an exception indicating that the file could not be cleared
//        }
        try (FileOutputStream outputStream = new FileOutputStream(Path.of(path).toFile(), true);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            outputStreamWriter.write(text);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

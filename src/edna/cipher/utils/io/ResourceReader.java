package edna.cipher.utils.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

// Класс, считывающий текст из файла
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceReader {

    public static String readFromFile(String path) {
        try (FileInputStream inputStream = new FileInputStream(Path.of(path).toFile())) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

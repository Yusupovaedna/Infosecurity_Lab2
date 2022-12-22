package edna.cipher.interfaces;

import java.io.UnsupportedEncodingException;

// Интерфейс, описывающий функционал любого шифра (шифрация + дешифрация)
public interface Cipher<T, S, K extends CipherKey, M extends CipherKey> {
    String encode(T text, K openCipherKey) throws UnsupportedEncodingException;

    String decode(S cipherText, M closedCipherKey) throws UnsupportedEncodingException;
}

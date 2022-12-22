package edna.cipher.impls.rijndael.cipherkey;

import edna.cipher.interfaces.CipherKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import edna.cipher.interfaces.CipherKey;

@Getter
@AllArgsConstructor
public class RijndaelKey implements CipherKey {
    public String key;
}

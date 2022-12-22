package edna.cipher.impls.rijndael.paths;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RijndaelPaths {

    CBC_ENCODE_LOG("src/edna/resources/textfiles/rijndael/log/cbc-encode-log.txt"),
    CBC_DECODE_LOG("src/edna/resources/textfiles/rijndael/log/cbc-decode-log.txt"),

    CBC_ENCODE_INPUT("src/edna/resources/textfiles/rijndael/cbc/encode/input.txt"),
    CBC_ENCODE_OUTPUT("src/edna/resources/textfiles/rijndael/cbc/encode/output.txt"),
    CBC_ENCODE_EXPECTED("src/main/resources/textfiles/rijndael/cbc/encode/expected.txt"),

    CBC_DECODE_INPUT("src/edna/resources/textfiles/rijndael/cbc/encode/output.txt"),
    CBC_DECODE_OUTPUT("src/edna/resources/textfiles/rijndael/cbc/decode/output.txt"),
    CBC_DECODE_EXPECTED("src/main/resources/textfiles/rijndael/cbc/decode/expected.txt");

    private final String path;
}

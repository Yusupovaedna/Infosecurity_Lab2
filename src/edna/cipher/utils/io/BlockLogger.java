package edna.cipher.utils.io;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockLogger {

    private String path;

    // Вывести блок данных с шестнадцатеричными числами
    public void printBlockHex(int[][] block) {

        ResourceWriter.writeToFile("{\n", path);

        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                ResourceWriter.writeToFile(Integer.toHexString(block[i][j]), path);

                if (!(i == block.length - 1 && j == block[i].length - 1)) {
                    ResourceWriter.writeToFile(", ", path);
                }
            }

            ResourceWriter.writeToFile("\n", path);
        }

        ResourceWriter.writeToFile("}\n\n", path);
    }

    public void println(String str) {
        ResourceWriter.writeToFile(str + "\n", path);
    }
}

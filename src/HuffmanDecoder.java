import java.util.HashMap;
import java.util.Map;

class HuffmanNode {
    char character;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode(char character) {
        this.character = character;
    }
}

public class HuffmanDecoder {

    public static HuffmanNode buildHuffmanTree(Map<Character, String> huffmanCodes) {
        HuffmanNode root = new HuffmanNode('\0');
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            char character = entry.getKey();
            String code = entry.getValue();
            HuffmanNode current = root;
            for (char bit : code.toCharArray()) {
                if (bit == '0') {
                    if (current.left == null) {
                        current.left = new HuffmanNode('\0');
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = new HuffmanNode('\0');
                    }
                    current = current.right;
                }
            }
            current.character = character;
        }
        return root;
    }

    public static String decode(String encodedString, HuffmanNode root) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode current = root;
        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decodedString.append(current.character);
                current = root;
            }
        }
        return decodedString.toString();
    }

    public static void main(String[] args) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        huffmanCodes.put('a', "0000");
        huffmanCodes.put('b', "10");
        huffmanCodes.put('c', "001");
        huffmanCodes.put('d', "11");

        String encodedString = "111100110100000 "; // Example encoded string
        HuffmanNode root = buildHuffmanTree(huffmanCodes);
        String decodedString = decode(encodedString, root);

        System.out.println("Decoded String: " + decodedString);
    }
}
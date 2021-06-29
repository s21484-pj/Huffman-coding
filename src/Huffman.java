import java.util.HashMap;

public class Huffman {
    private HashMap<Character, String> map = new HashMap<>();

    public void printCode(HuffmanNode root, String s) {
        if (root.left == null
                && root.right == null
                && Character.isLetter(root.c)) {

            // c is the character in the node
            System.out.println(root.c + ":" + s);
            map.put(root.c, s);

            return;
        }

        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public HashMap<Character, String> getMap() {
        return map;
    }
}
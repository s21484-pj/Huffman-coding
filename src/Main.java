import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String str = "";

        // read data from file
        try {
            File myObj = new File("C:\\Users\\Adm\\Desktop\\asd huffman\\input.txt");
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                str = reader.nextLine();
                System.out.println("Message read from file: " + str);
                System.out.println("---------");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        ArrayList<Character> charList = new ArrayList<>();
        ArrayList<Integer> charFreq = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            } else if (charList.contains(c)) {
                continue;
            } else {
                for (int j = 0; j < str.length(); j++) {
                    if (c == str.charAt(j)) {
                        count++;
                    }
                }
            }
            charList.add(c);
            charFreq.add(count);
            count = 0;
        }

        int n = 0;
        if (charList.size() == charFreq.size()) {
            n = charList.size();
        }

        // creating a priority queue makes a min-priority queue (min-heap)
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(n, new MyComparator());

        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = charList.get(i);
            hn.data = charFreq.get(i);
            hn.left = null;
            hn.right = null;
            queue.add(hn);
        }
        HuffmanNode root = null;

        // extract the two minimum value from the heap each time until
        // its size reduces to 1, extract until all the nodes are extracted.
        while (queue.size() > 1) {
            HuffmanNode x = queue.peek();
            queue.poll();

            HuffmanNode y = queue.peek();
            queue.poll();

            HuffmanNode f = new HuffmanNode();

            // to the sum of the frequency of the two nodes assigning values to the f node.
            f.data = x.data + y.data;
            f.c = '-';

            // first extracted node as left child.
            f.left = x;

            // second extracted node as the right child.
            f.right = y;

            // marking the f node as the root node.
            root = f;
            queue.add(f);
        }

        for (int i = 0; i < charList.size(); i++) {
            System.out.println(charList.get(i) + ":" + charFreq.get(i));
        }
        System.out.println("---------");

        Huffman huffman = new Huffman();
        huffman.printCode(root, "");
        System.out.println("---------");

        Map<Character, String> map = huffman.getMap();
        Set<Map.Entry<Character, String>> entrySet = map.entrySet();

//        for (Map.Entry<Character, String> entry: entrySet) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                for (Map.Entry<Character, String> entry: entrySet) {
                    if (c == entry.getKey()) {
                        System.out.print(entry.getValue() + " ");
                    }
                }
            }
        }
        System.out.println("\n---------");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int chooseOperation;

        while (true) {
            System.out.println("Choose operation:\n" +
                    " 1. Save to file\n" +
                    " 2. Read from file\n" +
                    " 0. Exit");
            chooseOperation = Integer.parseInt(reader.readLine());

            switch (chooseOperation) {
                case 1:
                    try {
                        System.out.println("Enter path to file");
                        // C:\Users\Adm\Desktop\asd huffman\output.txt
                        Scanner temp = new Scanner(System.in);
                        FileWriter writer = new FileWriter(temp.nextLine());

                        // write encoded message to file
                        for (Map.Entry<Character, String> entry: entrySet) {
                            writer.write(entry.getKey() + ":" + entry.getValue() + "\r\n");
                        }
                        for (int i = 0; i < str.length(); i++) {
                            char c = str.charAt(i);
                            if (!Character.isWhitespace(c)) {
                                for (Map.Entry<Character, String> entry: entrySet) {
                                    if (c == entry.getKey()) {
                                        writer.write(entry.getValue() + " ");
                                    }
                                }
                            }
                        }

                        writer.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    System.out.println();
                    break;

                case 2:
                    ArrayList<String> output = new ArrayList<>();
                    HashMap<Character, String> outputMap = new HashMap<>();

                    // read from file
                    try {
                        File file = new File("C:\\Users\\Adm\\Desktop\\asd huffman\\output.txt");
                        Scanner read = new Scanner(file);
                        while (read.hasNextLine()) {
                            output.add(read.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        e.printStackTrace();
                    }

                    // read content
                    System.out.println("File content:");
                    for (String s : output) {
                        System.out.println(s);
                    }

                    // list to map
                    for (int i = 0; i < output.size() - 1; i++) {
                        StringBuilder builder = new StringBuilder();
                        for (int j = 0; j < output.get(i).length(); j++) {
                            if (j > 1) {
                                builder.append(output.get(i).charAt(j));
                            }
                        }
                        String temp = String.valueOf(builder);
                        outputMap.put(output.get(i).charAt(0), temp);
                    }
//                    System.out.println("Map content:");
                    Set<Map.Entry<Character, String>> outputMapSet = outputMap.entrySet();
//                    for (Map.Entry<Character, String> x: outputMapSet) {
//                        System.out.println(x.getKey() + ":" + x.getValue());
//                    }
                    System.out.println("----------");
                    System.out.println("Decoded message:");
                    String code = output.get(output.size() - 1);
                    ArrayList<String> decodedMessage = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();

                    // decode message
                    for (int i = 0; i < code.length(); i++) {
                        if (!Character.isWhitespace(code.charAt(i))) {
                            builder.append(code.charAt(i));
                        } else if (i == code.length() - 1) {
                            String temp = String.valueOf(builder);
                            decodedMessage.add(temp);
                            builder = new StringBuilder();
                        } else {
                            String temp = String.valueOf(builder);
                            decodedMessage.add(temp);
                            builder = new StringBuilder();
                        }
                    }
                    builder = new StringBuilder();
                    for (int i = 0; i < decodedMessage.size(); i++) {
                        for (Map.Entry<Character, String> x : outputMapSet) {
                            if (decodedMessage.get(i).equals(x.getValue())) {
                                builder.append(x.getKey());
                            }
                        }
                    }
                    System.out.println(builder);

                    // write message to file
                    try {
                        System.out.println("Enter path to file");
                        // C:\Users\Adm\Desktop\asd huffman\decoded.txt
                        Scanner temp = new Scanner(System.in);
                        FileWriter writer = new FileWriter(temp.nextLine());
                        writer.write(builder.toString());
                        writer.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    System.out.println();
                    break;

                default:
                    break;
            }
            if (chooseOperation == 0) {
                break;
            }
        }
    }
}

// test
// C:\Users\Adm\Desktop\asd huffman\output.txt
// C:\Users\Adm\Desktop\asd huffman\decoded.txt

import java.util.*;
import java.io.*;

/**
 * _Main
 * @author creativitRy
 */
public class day7_2 {
    HashMap<String, Node> map;
    
    private void run() throws FileNotFoundException {
        InputReader in = new InputReader("day7_input.in");
        /*in = new InputReader(new StringReader("pbga (66)\n" +
            "xhth (57)\n" +
            "ebii (61)\n" +
            "havc (66)\n" +
            "ktlj (57)\n" +
            "fwft (72) -> ktlj, cntj, xhth\n" +
            "qoyq (66)\n" +
            "padx (45) -> pbga, havc, qoyq\n" +
            "tknk (41) -> ugml, padx, fwft\n" +
            "jptl (61)\n" +
            "ugml (68) -> gyxo, ebii, jptl\n" +
            "gyxo (61)\n" +
            "cntj (57)"));*/
        
        map = new HashMap<>();
        HashMap<String, Node> processStack = new HashMap<>();
        
        while (true) {
            String line = in.nextLine();
            if (line == null || line.isEmpty())
                break;
            
            Scanner scn = new Scanner(line);
            String name = scn.next();
            int val = Integer.parseInt(scn.next().replaceAll("[()]", ""));
            Node node = new Node(name, val);
            map.put(name, node);
            if (scn.hasNext()) {
                scn.next();
                while (scn.hasNext()) {
                    String child = scn.next().replaceFirst(",", "");
                    node.children.add(child);
                    processStack.put(child, node);
                }
            }
        }
        
        for (Map.Entry<String, Node> entry : processStack.entrySet()) {
            map.get(entry.getKey()).par = entry.getValue();
        }
        
        Node root = null;
        for (Map.Entry<String, Node> entry : map.entrySet()) {
            if (entry.getValue().par == null) {
                root = entry.getValue();
                break;
            }
        }
        
        rec(root);
    }
    
    private int rec(Node root) {
        if (root == null)
            return 0;
        if (root.children.isEmpty())
            return root.val;
        
        int a = -1;
        int b = -1;
        boolean aWeightIsCorrect = false;
        Node aChild = null;
    
        int sum = 0;
        for (String child : root.children) {
            Node node = map.get(child);
            int weight = rec(node);
            
            if (a == -1) {
                a = weight;
                aChild = node;
            }
            else if (a == weight) {
                aWeightIsCorrect = true;
            }
            else {
                if (aWeightIsCorrect) {
                    System.out.println(a - weight + node.val);
                    System.exit(0);
                }
                if (b == -1)
                    b = weight;
                else {
                    System.out.println(b - a + aChild.val);
                    System.exit(0);
                }
            }
            sum += weight;
        }
        
        return sum + root.val;
    }
    
    public class Node {
        String name;
        int val;
        Node par;
        ArrayList<String> children;
        
        public Node(String name, int val) {
            this.name = name;
            this.val = val;
            children = new ArrayList<>();
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        new day7_2().run();
    }
    
    public class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        
        public InputReader(String fileName) throws FileNotFoundException {
            this(new FileReader(new File(fileName)));
        }
        
        public InputReader(InputStream in) {
            this(new InputStreamReader(in));
        }
        
        public InputReader(Reader reader) {
            this.reader = new BufferedReader(reader, 32768);
            tokenizer = null;
        }
        
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
        public double nextDouble() {
            return Double.parseDouble(next());
        }
        
        public long nextLong() {
            return Long.parseLong(next());
        }
        
        /**
         * When you call next(), that entire line will be skipped.
         * No flushing buffers.
         * Doesn't work when you want to scan the remaining line.
         * @return entire line or null if at the end
         */
        public String nextLine() {
            try {
                String str = reader.readLine();
                tokenizer = null;
                return str;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

import java.util.*;
import java.io.*;

/**
 * _Main
 * @author creativitRy
 */
public class day7_1 {
    HashMap<String, Node> map;
    
    private void run() throws FileNotFoundException {
        InputReader in = new InputReader("day7_input.in");
    
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
    
        for (Map.Entry<String, Node> entry : map.entrySet()) {
            if (entry.getValue().par == null)
            {
                System.out.println(entry.getKey());
                return;
            }
        }
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
        new day7_1().run();
        
    }
    
    public class InputReader
    {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        
        public InputReader(String fileName) throws FileNotFoundException
        {
            this(new FileReader(new File(fileName)));
        }
        
        public InputReader(InputStream in)
        {
            this(new InputStreamReader(in));
        }
        
        public InputReader(Reader reader)
        {
            this.reader = new BufferedReader(reader, 32768);
            tokenizer = null;
        }
        
        public String next()
        {
            while (tokenizer == null || !tokenizer.hasMoreTokens())
            {
                try
                {
                    tokenizer = new StringTokenizer(reader.readLine());
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        
        public int nextInt()
        {
            return Integer.parseInt(next());
        }
        
        public double nextDouble()
        {
            return Double.parseDouble(next());
        }
        
        public long nextLong()
        {
            return Long.parseLong(next());
        }
        
        /**
         * When you call next(), that entire line will be skipped.
         * No flushing buffers.
         * Doesn't work when you want to scan the remaining line.
         *
         * @return entire line or null if at the end
         */
        public String nextLine()
        {
            try
            {
                String str = reader.readLine();
                tokenizer = null;
                return str;
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

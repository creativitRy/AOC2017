import java.util.*;
import java.io.*;

/**
 * Main
 * @author creativitRy
 */
public class Main {
    private void run() throws FileNotFoundException {
        InputReader in = new InputReader("day8_input.in");
        // in = new InputReader(new StringReader("b inc 5 if a > 1\n" +
        //     "a inc 1 if b < 5\n" +
        //     "c dec -10 if a >= 1\n" +
        //     "c inc -20 if c == 10"));
        
        HashMap<String, Integer> vars = new HashMap<>();
    
    
        int max = Integer.MIN_VALUE;
        while (true) {
            String line = in.nextLine();
            if (line == null || line.isEmpty())
                break;
            
            Scanner scn = new Scanner(line);
            String var1 = scn.next();
            boolean inc = scn.next().equals("inc");
            int change = scn.nextInt();
            
            scn.next();
            
            String var2 = scn.next();
            String sign = scn.next();
            int comp = scn.nextInt();
            Integer val2 = vars.get(var2);
            if (val2 == null)
                val2 = 0;
            
            if (sign.equals("==") && val2 != comp)
                continue;
            if (sign.equals("!=") && val2 == comp)
                continue;
            if (sign.equals(">=") && val2 <  comp)
                continue;
            if (sign.equals(">") &&  val2 <= comp)
                continue;
            if (sign.equals("<=") && val2 >  comp)
                continue;
            if (sign.equals("<") &&  val2 >= comp)
                continue;
            
            Integer val1 = vars.get(var1);
            if (val1 == null)
                val1 = 0;
            if (inc)
                vars.put(var1, val1 + change);
            else
                vars.put(var1, val1 - change);
        }
        
        for (Map.Entry<String, Integer> entry : vars.entrySet()) {
            max = Math.max(max, entry.getValue());
        }
        System.out.println(max);
    }
    
    
    public static void main(String[] args) throws IOException {
        new Main().run();
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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main
 * @author creativitRy
 */
public class Main {
    private void run() throws FileNotFoundException {
        InputReader in = new InputReader("day18_input.in");
        
        ArrayList<Instruction> instructions = new ArrayList<>();
        
        while (true) {
            String line = in.nextLine();
            if (line == null || line.isEmpty())
                break;
            
            Instruction instruction;
            if (line.startsWith("snd"))
                instruction = new Snd(line);
            else if (line.startsWith("set"))
                instruction = new Set(line);
            else if (line.startsWith("add"))
                instruction = new Add(line);
            else if (line.startsWith("mul"))
                instruction = new Mul(line);
            else if (line.startsWith("mod"))
                instruction = new Mod(line);
            else if (line.startsWith("rcv"))
                instruction = new Rcv(line);
            else
                instruction = new Jgz(line);
            instructions.add(instruction);
        }
        
        int index = 0;
        HashMap<Character, Long> map = new HashMap<>();
        while (!Rcv.found) {
            Instruction instruction = instructions.get(index);
            index += instruction.run(map, null, null);
        }
        
        System.out.println(Snd.last);
    }
    
    interface Value {
        long get(HashMap<Character, Long> map);
    }
    
    static class Literal implements Value {
        private long value;
        
        public Literal(String value) {
            this.value = Long.parseLong(value);
        }
        
        @Override
        public long get(HashMap<Character, Long> map) {
            return value;
        }
    }
    
    static class Variable implements Value {
        private char var;
        
        public Variable(String var) {
            this.var = var.charAt(0);
        }
        
        @Override
        public long get(HashMap<Character, Long> map) {
            Long temp = map.get(var);
            return temp == null ? 0 : temp;
        }
    }
    
    private static Value get(String parse) {
        if (parse.matches("-?\\d+"))
            return new Literal(parse);
        else
            return new Variable(parse);
    }
    
    interface Instruction {
        int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other);
    }
    
    static class Snd implements Instruction {
        public static long last;
        private Value val;
        
        public Snd(String line) {
            val = get(line.substring(4).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            last = val.get(map);
            return 1;
        }
    }
    
    class Set implements Instruction {
        private char var;
        private Value val;
        
        public Set(String line) {
            var = line.charAt(4);
            val = get(line.substring(6).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            map.put(var, val.get(map));
            return 1;
        }
    }
    
    class Add implements Instruction {
        private char var;
        private Value prev;
        private Value val;
        
        public Add(String line) {
            var = line.charAt(4);
            prev = get("" + var);
            val = get(line.substring(6).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            map.put(var, prev.get(map) + val.get(map));
            return 1;
        }
    }
    
    class Mul implements Instruction {
        private char var;
        private Value prev;
        private Value val;
        
        public Mul(String line) {
            var = line.charAt(4);
            prev = get("" + var);
            val = get(line.substring(6).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            map.put(var, prev.get(map) * val.get(map));
            return 1;
        }
    }
    
    class Mod implements Instruction {
        private char var;
        private Value prev;
        private Value val;
        
        public Mod(String line) {
            var = line.charAt(4);
            prev = get("" + var);
            val = get(line.substring(6).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            map.put(var, prev.get(map) % val.get(map));
            return 1;
        }
    }
    
    static class Rcv implements Instruction {
        public static boolean found = false;
        private char var;
        
        public Rcv(String line) {
            var = line.charAt(line.length() - 1);
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            Long num = map.get(var);
            if (num != null && num != 0)
                found = true;
            return 1;
        }
    }
    
    class Jgz implements Instruction {
        private char var;
        private Value prev;
        private Value val;
        
        public Jgz(String line) {
            var = line.charAt(4);
            prev = get("" + var);
            val = get(line.substring(6).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            if (prev.get(map) > 0)
                return (int) val.get(map);
            return 1;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
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

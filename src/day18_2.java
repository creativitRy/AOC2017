import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main
 * @author creativitRy
 */
public class Main {
    private long ans = 0;
    private ArrayList<Instruction> instructions;
    
    private void run() throws FileNotFoundException {
        InputReader in = new InputReader("day18_input.in");
        
        instructions = new ArrayList<>();
        
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
        
        HashMap<Character, Long> map = new HashMap<>();
        map.put('p', 0L);
        HashMap<Character, Long> map2 = new HashMap<>();
        map2.put('p', 1L);
        LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<Long>(){
            @Override
            public boolean add(Long aLong) {
                ans++;
                return super.add(aLong);
            }
        };
        LinkedBlockingQueue<Long> queue2 = new LinkedBlockingQueue<>();
        Program program1 = new Program(map, queue, queue2);
        Program program2 = new Program(map2, queue2, queue);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(program1);
        executor.execute(program2);
        
        executor.shutdown();
        int temp = 0;
        while (!executor.isTerminated()) {
            if (program1.blocked && program2.blocked)
            {
                if (temp > 999999) // lol
                    break;
                else
                    temp++;
            }
            else
                temp = 0;
        }
        System.out.println(ans);
        System.exit(0);
    }
    
    class Program implements Runnable
    {
        private HashMap<Character, Long> map;
        private LinkedBlockingQueue<Long> queue;
        private LinkedBlockingQueue<Long> other;
        int index;
        boolean blocked;
    
        public Program(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            this.map = map;
            this.queue = queue;
            this.other = other;
        }
    
        @Override
        public void run() {
            while (true) {
                Instruction instruction = instructions.get(index);
                if (instruction instanceof Rcv)
                    blocked = true;
                index += instruction.run(map, queue, other);
                blocked = false;
            }
        }
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
        private Value val;
        
        public Snd(String line) {
            val = get(line.substring(4).trim());
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            other.add(val.get(map));
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
        private char var;
        
        public Rcv(String line) {
            var = line.charAt(line.length() - 1);
        }
        
        @Override
        public int run(HashMap<Character, Long> map, LinkedBlockingQueue<Long> queue, LinkedBlockingQueue<Long> other) {
            try {
                map.put(var, queue.take());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }
    
    class Jgz implements Instruction {
        private Value prev;
        private Value val;
        
        public Jgz(String line) {
            prev = get("" + line.charAt(4));
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

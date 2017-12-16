import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main
 * @author creativitRy
 */
public class Main {
    
    private void run() throws FileNotFoundException {
        ArrayList<Move> moves = new ArrayList<>();
        Scanner in = new Scanner(new File("day16_input.in"));
        in.useDelimiter(",");
        
        while (in.hasNext()) {
            String s = in.next().trim();
            if (s.charAt(0) == 's')
                moves.add(new S(Integer.parseInt(s.substring(1))));
            else if (s.charAt(0) == 'x') {
                String[] temp = s.substring(1).split("/");
                moves.add(new X(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
            }
            else
                moves.add(new P(s.charAt(1), s.charAt(3)));
        }
        
        WrapArray array = new WrapArray();
        for (Move move : moves)
            move.move(array);
        System.out.println(array);
    }
    
    static class WrapArray {
        char[] array;
        int head;
        
        public WrapArray() {
            array = new char[16];
            for (int i = 0; i < 16; i++)
                array[i] = (char) (i + 'a');
        }
        
        public WrapArray(WrapArray copy) {
            this.array = new char[16];
            System.arraycopy(copy.array, 0, array, 0, 16);
            this.head = copy.head;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WrapArray oo = (WrapArray) o;
            
            for (int i = 0; i < 16; i++)
                if (array[(i + head) & 15] != oo.array[(i + oo.head) & 15])
                    return false;
            return true;
        }
        
        @Override
        public int hashCode() {
            int result = array[head & 15] - 'a';
            for (int i = 1; i < 16; i++)
                result = (result << 4) + (array[(i + head) & 15] - 'a');
            
            return result;
        }
        
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < 16; i++)
                s.append(array[(i + head) & 15]);
            return s.toString();
        }
    }
    
    interface Move {
        void move(WrapArray array);
    }
    
    static class S implements Move {
        int num;
        
        public S(int num) {
            this.num = num;
        }
        
        @Override
        public void move(WrapArray array) {
            for (int i = 0; i < num; i++)
                array.head = (array.head + 15) & 15;
        }
    }
    
    static class X implements Move {
        int a;
        int b;
        
        public X(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void move(WrapArray array) {
            int a = (this.a + array.head) & 15;
            int b = (this.b + array.head) & 15;
            
            char temp2 = array.array[a];
            array.array[a] = array.array[b];
            array.array[b] = temp2;
        }
    }
    
    static class P implements Move {
        char a;
        char b;
        
        public P(char a, char b) {
            this.a = a;
            this.b = b;
        }
        
        @Override
        public void move(WrapArray array) {
            int a = -1, b = -1;
            for (int i = 0; i < 16; i++) {
                if (array.array[i] == this.a) {
                    a = i;
                    if (b != -1)
                        break;
                }
                else if (array.array[i] == this.b) {
                    b = i;
                    if (a != -1)
                        break;
                }
            }
            
            char temp2 = array.array[a];
            array.array[a] = array.array[b];
            array.array[b] = temp2;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        new Main().run();
    }
}

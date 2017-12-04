import java.util.*;

public class day3_2 {
    public static void main(String[] args) {
        int input = 747;

        HashMap<Vec, Integer> map = new HashMap<>();

        Vec v = new Vec(0, 0);
        Vec dir = new Vec(0, -1);
        int sum = 1;

        for (;;) {

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    Integer n = map.get(v.add(new Vec(x, y)));
                    if (n != null)
                        sum += n;
                }
            }

            if (sum > input) {
                System.out.println(sum);
                break;
            }

            map.put(v, sum);

            if (v.x == v.y || (v.x < 0 && v.x == -v.y) || (v.x > 0 && v.x == 1 - v.y))
                dir = dir.flip();
            v = v.add(dir);

            sum = 0;
        }
    }

    private static class Vec {
        public int x;
        public int y;

        public Vec(int xx, int yy) {
            x = xx;
            y = yy;
        }

        public Vec add(Vec o) {
            return new Vec(x + o.x, y + o.y);
        }

        public Vec flip() {
            return new Vec(-y, x);
        }

        public String toString() {
            return x + ", " + y;
        }

        public int hashCode() {
            return x * 255 + y;
        }

        public boolean equals(Object other) {
            Vec o = (Vec) other;
            return x == o.x && y == o.y;
        }
    }
}
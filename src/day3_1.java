public class day3_1 {
    public static void main(String[] args) {
        int input = 1024;

        Vec v = new Vec(0, 0);
        Vec dir = new Vec(0, -1);

        for (int i = 1; i < input; i++) {

            if (v.x == v.y || (v.x < 0 && v.x == -v.y) || (v.x > 0 && v.x == 1 - v.y))
                dir = dir.flip();
            v = v.add(dir);
        }

        //System.out.println(v);

        System.out.println(Math.abs(v.x) + Math.abs(v.y));
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
    }
}
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tk = new StringTokenizer(in.readLine());
        int m = Integer.parseInt(tk.nextToken());
        int n = Integer.parseInt(tk.nextToken());

        FloodFill ff = new FloodFill(m, n);

        for (int i = 0; i < m; i++) {
            String inp = in.readLine();
            for (int j = 0; j < n; j++) {
                ff.grid[i][j] = inp.charAt(j);
            }
        }

        System.out.println(ff.fill());

        in.close();
        out.close();
    }
}

class FloodFill {
    char[][] grid;
    int m, n;

    FloodFill(int m, int n) {
        grid = new char[m][n];
        this.m = m;
        this.n = n;
    }

    class Coord {
        int x;
        int y;

        Coord(int i, int j) {
            x = i;
            y = j;
        }

        @Override
        public String toString() {
            return "Y: " + y + " X: " + x;
        }
    }

    boolean check(int y, int x) {
        if (y >= m || 0 > y) {
            return false;
        }

        if (x >= n || 0 > x) {
            return false;
        }

        return true;
    }

    int fill() {
        boolean[][] mask = new boolean[m][n];
        Queue<Coord> buf = new LinkedList<Coord>();
        int count = 0;

        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[j][i] == 'L') {
                    buf.offer(new Coord(i, j));
                }

                if (grid[j][i] == 'C') {
                    grid[j][i] = 'L'; // how optimistic!
                }
            }
        }

        while (!buf.isEmpty()) {
            Coord src = buf.poll();
            Queue<Coord> buf2 = new LinkedList<Coord>();

            if (!mask[src.y][src.x]) {
                buf2.offer(src);
                count++;
            }

            while (!buf2.isEmpty()) {
                Coord cur = buf2.poll();
                int yt, xt;

                if (!mask[cur.y][cur.x]) {
                    mask[cur.y][cur.x] = true;
                } else {
                    continue;
                }

                yt = cur.y + 1; xt = cur.x;
                if (check(yt, xt) && grid[yt][xt] == 'L') {
                    buf2.offer(new Coord(xt, yt));
                }

                yt = cur.y - 1; xt = cur.x;
                if (check(yt, xt) && grid[yt][xt] == 'L') {
                    buf2.offer(new Coord(xt, yt));
                }

                yt = cur.y; xt = cur.x + 1;
                if (check(yt, xt) && grid[yt][xt] == 'L') {
                    buf2.offer(new Coord(xt, yt));
                }

                yt = cur.y; xt = cur.x - 1;
                if (check(yt, xt) && grid[yt][xt] == 'L') {
                    buf2.offer(new Coord(xt, yt));
                }
            }
        }
        
        return count;
    }

    @Override
    public String toString() {
        String out = "";

        out += "Grid:\n";
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                out += " " + grid[j][i];
            }
            out += "\n";
        }

        out += "\n";

        out += "FloodFill Segments: " + fill();
            
        return out;
    }
}

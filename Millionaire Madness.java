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

        int[][] grid = new int[m][n];

        for (int i = 0; i < m; i++) {
            tk = new StringTokenizer(in.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(tk.nextToken());
            }
        }
        
        Prim prim = new Prim(m, n, grid);

        out.write(Integer.toString(prim.minimax()));

        in.close();
        out.close();
    }
}

class Prim {
    PriorityQueue<Node> pq;
    boolean[][] hist;
    int[][] grid;
    int m, n;

    Prim(int m, int n, int[][] grid) {
        pq = new PriorityQueue<Node>();
        hist = new boolean[m][n];
        this.grid = grid;
        this.m = m;
        this.n = n;
    }

    boolean check(int y, int x) {

        if (y >= m || 0 > y) {
            return false;
        }

        if (x >= n || 0 > x) {
            return false;
        }

        if (hist[y][x]) {
            return false;
        }

        return true;
    }

    int minimax() {
        int cost, xt, yt;

        pq.add(new Node());

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (!hist[cur.y][cur.x]) {
                if (cur.y == (m - 1) && 
                    cur.x == (n - 1)) {
                    return cur.value;
                }

                hist[cur.y][cur.x] = true;

                yt = cur.y + 1; xt = cur.x;
                if (check(yt, xt)) {
                    cost = Math.max(
                        grid[yt][xt] - grid[cur.y][cur.x],
                        cur.value);
                    pq.add(new Node(yt, xt, cost));
                }

                yt = cur.y - 1; xt = cur.x;
                if (check(yt, xt)) {
                    cost = Math.max(
                        grid[yt][xt] - grid[cur.y][cur.x],
                        cur.value);
                    pq.add(new Node(yt, xt, cost));
                }

                yt = cur.y; xt = cur.x + 1;
                if (check(yt, xt)) {
                    cost = Math.max(
                        grid[yt][xt] - grid[cur.y][cur.x],
                        cur.value);
                    pq.add(new Node(yt, xt, cost));
                }

                yt = cur.y; xt = cur.x - 1;
                if (check(yt, xt)) {
                    cost = Math.max(
                        grid[yt][xt] - grid[cur.y][cur.x],
                        cur.value);
                    pq.add(new Node(yt, xt, cost));
                }
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        String out = "";

        out += "Grid: \n";
        for (int[] row : grid) {
            out += "    ";
            for (int e : row) {
                out += e + " ";
            }
            out += "\n";
        }
        out += "\n";
        out += "Cost: " + minimax();

        return out;
    }

}

class Node implements Comparable<Node> {
    int value;
    int x;
    int y;

    Node() {
        this.value = 0;
        this.y = 0;
        this.x = 0;
    }

    Node(int y, int x, int value) {
        this.value = value;
        this.y = y;
        this.x = x;
    }

    @Override
    public int compareTo(Node other) {
        return this.value - other.value;
    }
}
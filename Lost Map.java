import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tk = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tk.nextToken());

        MST mst = new MST(n);

        for (int i = 0; i < n; i++) {
            tk = new StringTokenizer(in.readLine());
            for (int j = 0; j < n; j++) {
                mst.add(i + 1, j + 1, Integer.parseInt(tk.nextToken()));  
            }
        }

        out.write(mst.solve());

        in.close();
        out.close();
    }
}

class MST {
    ArrayList<ArrayList<Pair>> edges;
    int n;

    MST(int _n) {
        edges = new ArrayList<ArrayList<Pair>>(_n);
        this.n = _n;

        while (_n-- >= 0) {
            edges.add(new ArrayList<Pair>());
        }
    }

    void add(int i, int j, int w) {
        edges.get(i).add(new Pair(j, w));
    }

    void travel(int x, PriorityQueue<Node> pq, boolean[] hist) {
        hist[x] = true;

        for (Pair e : edges.get(x)) {
            if (!hist[e.j]) {
                pq.add(new Node(e.j, x, e.w));
            }
        }
    }

    String solve() {
        String out = "";

        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        boolean[] hist = new boolean[n + 1];
        
        travel(1, pq, hist);

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (!hist[cur.x]) {
                out += cur.y + " " + cur.x + "\n";

                travel(cur.x, pq, hist);
            } 
        }

        return out;
    }

    @Override
    public String toString() {
        String out = "";
        for (ArrayList<Pair> e : edges) {
            out += e + "\n";
        }
        return out;
    }
}

class Pair {
    int j;
    int w;

    Pair(int j, int w) {
        this.j = j;
        this.w = w;
    }

    @Override
    public String toString() {
        return j + " " + w;
    }

}

class Node implements Comparable<Node> {
    int value;
    int x;
    int y;

    Node() {
        this.x = 0;
        this.y = 0;
        this.value = 0;
    }

    Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(Node other) {
        return this.value - other.value;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, value);
    }
}
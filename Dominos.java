import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tk = new StringTokenizer(in.readLine());
        int z = Integer.parseInt(tk.nextToken());

        while (z-- > 0) {
            tk = new StringTokenizer(in.readLine());
            int n = Integer.parseInt(tk.nextToken());
            int m = Integer.parseInt(tk.nextToken());

            Graph graph = new Graph(n);
            while (m-- > 0) {
                tk = new StringTokenizer(in.readLine());
                int x = Integer.parseInt(tk.nextToken());
                int y = Integer.parseInt(tk.nextToken());

                graph.add(x, y);
            }

            out.write(Integer.toString(graph.kosaraju()) + "\n");
            
        }

        in.close();
        out.close();
    }

}

class Graph {
    List<List<Integer>> adj;
    boolean verbose = false;

    Graph(int n) {
        adj = new ArrayList<List<Integer>>(n + 1);

        while (n-- > -1) {
            adj.add(new ArrayList<Integer>());
        }
    }

    void add(int vertex, int value) {
        if (vertex != value) {
            adj.get(vertex).add(value);
        }
    }

    void dfs(List<List<Integer>> mat,
             boolean[] hist, 
             List<Integer> notstack, 
             int cur) {

        hist[cur] = true;
        

        for (Integer e : mat.get(cur)) {
            if (!hist[e]) {
                dfs(mat, hist, notstack, e);

                if (verbose) {
                    System.out.print("[DFS] " + e + "\n");
                }
            }
        }

        notstack.add(cur);
    }

    int kosaraju() {
        int n  = adj.size() - 1;
        boolean[] hist = new boolean[n + 1];

        int out = 0;

        List<Integer> notstack = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            if (!hist[i]) {
               dfs(adj, hist, notstack, i); 
            }
        }

        if (verbose) {
            System.out.print("Pre Reverse: \n   ");
            for(var e:notstack)
                System.out.print(e+" ");System.out.println();
            System.out.print("\n");
        }

        Collections.reverse(notstack);

        if (verbose) {
            System.out.print("Post Reverse: \n   ");
            for(var e:notstack)
                System.out.print(e+" ");System.out.println();
            System.out.print("\n");
        }

        hist = new boolean[n + 1];
        for (int i = 0; i < notstack.size(); i++) {
            int e = notstack.get(i);

            if (!hist[e]) {
                dfs(adj, hist, notstack, e);
                out++;
            }
        }

        return out;
    }

    @Override
    public String toString() {
        String out = "";
        out += "Grid: ";

        for (List<Integer> row : adj) {
            out += "    ";
            for (Integer e : row) {
                out += e.toString() + "     ";
            }
            out += "\n";
        }

        verbose = true;
        out += "\nSCC no incoming vertex: " + kosaraju();
        verbose = false;

        return out;
    }
}
import java.util.*;
import java.io.*;

public class Almostunionfind {

    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int n, m, op, p, q;

        String inp = in.readLine();
        while (inp != null && inp.length() != 0) {
            

            StringTokenizer tk = new StringTokenizer(inp);
            n = Integer.parseInt(tk.nextToken());
            m = Integer.parseInt(tk.nextToken());

            AUFDS sets = new AUFDS(n);

            while (m-- > 0) {
                tk = new StringTokenizer(in.readLine());

                op = Integer.parseInt(tk.nextToken());
                p = Integer.parseInt(tk.nextToken());

                switch (op){
                    case 1:
                        q = Integer.parseInt(tk.nextToken());
                        sets.union(p, q);
                        break;

                    case 2:
                        q = Integer.parseInt(tk.nextToken());
                        sets.move(p, q);
                        break;

                    case 3:
                        out.write(sets.ret(p) + "\n");
                        break;
                }
            }

            
            inp = in.readLine();
        }

        in.close();
        out.close();
    }
}

class AUFDS {
    int[] parents;
    int[] counts;
    long[] sums;
    int n;

    public AUFDS(int _n) {
        n = _n;
        parents = new int[2*(n+2)];
        counts  = new int[2*(n+2)];
        sums    = new long[2*(n+2)];

        for (int i = 1; i < n+1; i++) {
            parents[i]     = i + n;
            parents[i+n]   = i + n;
            counts[i+n]    = 1;
            sums[i+n]      = i;
        }
    }

    public int parent(int node) {

        while (parents[node] != node) {
            node = parents[node];
        }

        return node;
    }

    public void union(int p, int q) {
        int pp = parent(p);
        int qp = parent(q);

        if (pp != qp) {
            parents[pp] = qp;

            sums[qp] += sums[pp];
            counts[qp] += counts[pp];
        }
    }

    public void move(int p, int q) {
        int pp = parent(p);
        int qp = parent(q);

        if(pp != qp) {
            parents[p] = qp;

            sums[qp] += p;
            sums[pp] -= p;
            counts[qp]++;
            counts[pp]--;
        }       

    }

    public String ret(int p) {
        int pp = parent(p);
        return counts[pp] + " " + sums[pp];
    }

    @Override 
    public String toString() {
        String out = "Sets: ";
        for (int i = 0; i < n + 1; i++) {
            out += "{ ";
            for (int j = 0; j < n + 1; j++) {
                if (i == parent(j)) {
                    out += j + " ";
                }
            }
            out += "}";
        }
        return out;
    }


}
import java.util.*;
import java.io.*;

public class Workstations {
    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tk = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tk.nextToken());
        int m = Integer.parseInt(tk.nextToken());

        PriorityQueue<Researcher> researchers = new PriorityQueue<Researcher>(); 
        PriorityQueue<Workstation> workstations = new PriorityQueue<Workstation>();

        int acc = 0;

        while (n-->0) {
            tk = new StringTokenizer(in.readLine());
            researchers.add(new Researcher(
                    Integer.parseInt(tk.nextToken()),
                    Integer.parseInt(tk.nextToken())
                    ));
        }

        while (!researchers.isEmpty()) {
            Researcher cur = researchers.poll();

            // drop busy stations
            while (!workstations.isEmpty() && workstations.peek().endTime + m < cur.a) {
                workstations.poll();
            }

            // check if next free station is used before lock
            if (!workstations.isEmpty() && 
                        cur.a >= workstations.peek().endTime &&
                        cur.a <= workstations.peek().endTime + m) {
                workstations.poll();
                acc++;
            }

            // add/create workstation based on next free time
            workstations.add(new Workstation(cur.a + cur.s));
        }
        
        out.write(Integer.toString(acc));

        in.close();
        out.close();
    }
}

class Researcher implements Comparable<Researcher> {
    public int a;
    public int s;

    public Researcher(int a, int s) {
        this.a = a;
        this.s = s;
    }

    @Override 
    public int compareTo(Researcher other) {
        return this.a - other.a;
    }
}

class Workstation implements Comparable<Workstation> {
    public int endTime;

    public Workstation(int endTime) {
        this.endTime = endTime;
    }

    @Override 
    public int compareTo(Workstation other) {
        return this.endTime - other.endTime;
    }
}
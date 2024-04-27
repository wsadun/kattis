import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        List<Point> points = new ArrayList<Point>();

        StringTokenizer tk = new StringTokenizer(in.readLine());
        Point start = Point.of(Double.parseDouble(tk.nextToken()), Double.parseDouble(tk.nextToken()));
        points.add(start);

        tk = new StringTokenizer(in.readLine());
        Point end = Point.of(Double.parseDouble(tk.nextToken()), Double.parseDouble(tk.nextToken()));

        tk = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tk.nextToken());

        while (n-- > 0) {
            tk = new StringTokenizer(in.readLine());
            points.add(Point.of(Double.parseDouble(tk.nextToken()), Double.parseDouble(tk.nextToken())));
        }

        points.add(end);
        
        Graph graph = new Graph(points);

        System.out.println(graph.floyd());

        in.close();
        out.close();
    }
}

class Graph {
    List<List<Double>> adj;

    Graph(List<Point> points) {
        Point start = points.get(0);
        Point end = points.get(points.size() - 1);

        adj = new ArrayList<List<Double>>(points.size());

        // create start row
        List<Double> temp = new ArrayList<Double>(points.size());
        for (int i = 0; i < points.size(); i++) {
            temp.add(start.dist(points.get(i)) / 5);
        }
        adj.add(temp);

        // create cannon rows
        for (int i = 1; i < points.size() - 1; i++) {
            temp = new ArrayList<Double>(points.size());
            for (int j = 0; j < points.size(); j++) {
                double res = points.get(i).dist(points.get(j));
                
                temp.add(Math.min(res / 5, 2 + Math.abs(res - 50) / 5));
            }
            adj.add(temp);
        }

        // create end row
        temp = new ArrayList<Double>(points.size());
        for (int i = 0; i < points.size(); i++) {
            temp.add(end.dist(points.get(i)) / 5);
        }
        adj.add(temp);
    }

    public double floyd() {
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j < adj.size(); j++) {
                for (int k = 0; k < adj.size(); k++) {
                    adj.get(j).set(k, Math.min(adj.get(j).get(k), 
                                               adj.get(j).get(i) + adj.get(i).get(k)));
                }
            }          
        }
        return adj.get(0).get(adj.size() - 1);
    }

    @Override
    public String toString() {
        String out = "";
        for (var row : adj) {
            out += row.toString() + "\n";
        }
        return out;
    }
}

class Point {
    double x;
    double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static Point of(double x, double y) {
        return new Point(x, y);
    }

    double dist(Point other) {
        return Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
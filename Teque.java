import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int numLines = Integer.parseInt(in.readLine());

        Teque teque = new Teque();

        while (numLines-- > 0) {
            StringTokenizer tk = new StringTokenizer(in.readLine());
            String op = tk.nextToken();
            int val = Integer.parseInt(tk.nextToken());

            switch(op) {
                case "push_back":
                    teque.pushBack(val);
                    break;
                case "push_front":                
                    teque.pushFront(val);
                    break;
                case "push_middle":                
                    teque.pushMid(val);
                    break;
                case "get":
                    out.write(Integer.toString(teque.get(val)));
                    out.write("\n");
                    break;
            }
        }
        in.close();
        out.close();
    }
}

class Teque {
    Deque front;
    Deque back;

    public Teque() {
        front = new Deque();
        back = new Deque();
    }

    public void balance() {
        if (front.size() < back.size()) { // back to front
            front.put(front.tail, back.get(back.head + 1));
            back.remove(back.head + 1);
            front.tail++;
            back.head++;
        } else if (front.size() > back.size() + 1) { // front to back
            back.put(back.head, front.get(front.tail - 1));
            front.remove(front.tail - 1);
            back.head--;
            front.tail--;
        }
    }

    public void pushBack(int val) {
        back.put(back.tail, val);
        back.tail++;
        balance();
    }

    public void pushFront(int val) {
        front.put(front.head, val);
        front.head--;
        balance();
    }

    public void pushMid(int val) {
        front.put(front.tail, val);
        front.tail++;
        balance();
    }

    public Integer get(int index) {
        if (index < front.size()) { // front deque
            return front.get(index + front.head + 1);
        } else { // back deque
            return back.get(index - front.size() + back.head  + 1);
        }
    }
}

class Deque {
    public HashMap<Integer, Integer> hashmap;
    public int head;
    public int tail;

    public Deque() {
        hashmap = new HashMap<Integer, Integer>();
        head = 0;
        tail = 1;
    }

    public Integer get(Integer index) {
        return hashmap.get(index);
    }

    public void put(Integer index, Integer val) {
        hashmap.put(index, val);
    }

    public int size() {
        return hashmap.size();
    }

    public void remove(Integer index) {
        hashmap.remove(index); 
    }
}
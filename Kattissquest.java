import java.util.*;
import java.io.*;
/*
 ,_     _
 |\\_,-~/
 / _  _ |    ,--.
(  @  @ )   / ,-'
 \  _T_/-._( (
 /         `. \
|         _  \ |
 \ \ ,  /      |
  || |-_\__   /
 ((_/`(____,-'
 Kattis Solution 
*/
public class Kattissquest {
    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        TreeMap<Integer, Queue<Integer>> quests = new TreeMap<>();
        int E = 0, G = 0, X = 0;

        int n = Integer.parseInt(in.readLine());
        while (n-- > 0) {
            StringTokenizer tk = new StringTokenizer(in.readLine());
            String op = tk.nextToken();

            switch (op) {
                case "add":
                    E = Integer.parseInt(tk.nextToken());
                    G = Integer.parseInt(tk.nextToken());
                    
                    if (quests.containsKey(E)){
                        quests.get(E).add(G);
                    } else {
                        Queue<Integer> newQuest = new PriorityQueue<>(Comparator.reverseOrder());
                        newQuest.add(G);
                        quests.put(E, newQuest);
                    }
                    break;

                case "query":
                    X = Integer.parseInt(tk.nextToken());

                    long goldAcc = 0;
                    Map.Entry<Integer, Queue<Integer>> cur = quests.floorEntry(X);

                    while (cur != null) {
                        Queue<Integer> curPQ = cur.getValue();
                        Integer questGold = curPQ.poll();
                        Integer questEnergy = cur.getKey();
                        
                        X -= questEnergy;
                        goldAcc += questGold;

                        if (curPQ.isEmpty()) {
                            quests.remove(questEnergy);
                        }

                        cur = quests.floorEntry(X);
                    }
                    out.write(goldAcc + "\n");
                    break;
            }
        }

        in.close();
        out.close();
    }
}


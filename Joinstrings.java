import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
/*
    _____   ___   _____  ____  _____                                      
   |_   _|.'   `.|_   _||_   \|_   _|                                     
     | | /  .-.  \ | |    |   \ | |                                       
 _   | | | |   | | | |    | |\ \| |                                       
| |__' | \  `-'  /_| |_  _| |_\   |_                                      
`.____.'  `.___.'|_____||_____|\____|                                     
  ______   _________  _______     _____  ____  _____   ______    ______   
.' ____ \ |  _   _  ||_   __ \   |_   _||_   \|_   _|.' ___  | .' ____ \  
| (___ \_||_/ | | \_|  | |__) |    | |    |   \ | | / .'   \_| | (___ \_| 
 _.____`.     | |      |  __ /     | |    | |\ \| | | |   ____  _.____`.  
| \____) |   _| |_    _| |  \ \_  _| |_  _| |_\   |_\ `.___]  || \____) | 
 \______.'  |_____|  |____| |___||_____||_____|\____|`._____.'  \______.' 
                             Kattis Solution 
*/
public class Joinstrings {
    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int numWords = Integer.parseInt(in.readLine());

        ArrayList<Node> nodes = new ArrayList<Node>(numWords + 1);
        nodes.add(new Node("")); // dummy init

        if (numWords == 1) {
            out.write(in.readLine());
        }

        for (int i = 1; i <= numWords; i++) {
            nodes.add(new Node(in.readLine()));
        }

        int a = 0, b = 0;
        for (int i = 1; i <= numWords - 1; i++) {
            StringTokenizer tk = new StringTokenizer(in.readLine());
            a = Integer.parseInt(tk.nextToken());
            b = Integer.parseInt(tk.nextToken());

            if(nodes.get(a).next == null) {
                nodes.get(a).next = nodes.get(b);
            } else {
                nodes.get(a).last.next = nodes.get(b);
            }
            nodes.get(a).last = nodes.get(b).last;
            
        }

        Node head = nodes.get(a);
        while (head != null)
        {
            out.write(head.word);
            head = head.next;
        }

        in.close();
        out.close();
    }
}

class Node {
    public String word;
    public Node next;
    public Node last;

    public Node(String word) {
        this.word = word;
        this.last = this;
    }
}
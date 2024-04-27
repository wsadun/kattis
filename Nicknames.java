import java.util.*;
import java.io.*;
/*
       _-_          
    /~~   ~~\       
 /~~         ~~\     █████╗ ██╗   ██╗██╗      ████████╗██████╗ ███████╗███████╗
{               }   ██╔══██╗██║   ██║██║      ╚══██╔══╝██╔══██╗██╔════╝██╔════╝
 \  _-     -_  /    ███████║██║   ██║██║         ██║   ██████╔╝█████╗  █████╗  
   ~  \\ //  ~      ██╔══██║╚██╗ ██╔╝██║         ██║   ██╔══██╗██╔══╝  ██╔══╝ 
_- -   | | _- _     ██║  ██║ ╚████╔╝ ███████╗    ██║   ██║  ██║███████╗███████╗
  _ -  | |   -_     ╚═╝  ╚═╝  ╚═══╝  ╚══════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚══════╝
      // \\                              Kattis Solution                                  
*/

public class Nicknames {
    public static void main(String[] args) throws IOException {
        // IO init
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        AVL<String> tree  = new AVL<String>();

        int n = Integer.parseInt(in.readLine());
        while (n-- > 0) {
            tree.insert(in.readLine());
        }

        n = Integer.parseInt(in.readLine());
        while (n-- > 0) {
            String nickname = in.readLine();
            out.write(Integer.toString(tree.rank(nickname.concat("{")) - tree.rank(nickname)).concat("\n"));
        }

        in.close();
        out.close();
    }
}

class Node <T extends Comparable<T>> {
    Node<T> left;
    Node<T> right;
    int height;
    Integer weight; // cache weight, will have wrong ans if insert() is called after weight()
    T value;

    Node(T store) {
        left = null;
        right = null;
        height = 0;
        value = store;
    }

    void update() {
        height = Math.max(left == null ? -1 : left.height, right == null ? -1 : right.height) + 1;
    }

    Node<T> rotateLeft() {
        Node<T> n1 = right;
        Node<T> n2 = right.left;

        n1.left = this;
        right = n2;

        this.update();
        n1.update();

        return n1;
    }

    Node<T> rotateRight() {
        Node<T> n1 = left;
        Node<T> n2 = left.right;

        n1.right = this;
        left = n2;

        this.update();
        n1.update();

        return n1;
    }

    int getImbalance() {
        return (right == null ? -1 : right.height) - (left == null ? -1 : left.height);
    }

    Node<T> balance() {
        update();
        int imbalance = getImbalance();

        if (imbalance < -1) {
            if (left != null && left.getImbalance() > 0) {
                left = left.rotateLeft();
            }
            return rotateRight();

        } else if (imbalance > 1) {
            if (right != null && right.getImbalance() < 0) {
                right = right.rotateRight();
            }
            return rotateLeft();
        }

        return this;
    }

    Node<T> insert(T store) {
        if (value.compareTo(store) > 0) {
            left = left ==  null ? new Node<T>(store) : left.insert(store);
        } else if (value.compareTo(store) < 0) {
            right = right ==  null ? new Node<T>(store) : right.insert(store);
        }
        return this.balance();
    }

    Integer weight() { 
        // cache weight, will have wrong ans if insert() is called after weight(). 
        // Only suitable for this kattis problem.
        if (weight != null) {
            return weight;
        }

        weight = (left == null ? 0 : left.weight()) + (right == null ? 0 : right.weight()) + 1;
        return weight;
    }

    @Override
    public String toString() {
        return this.toString(this, 0);
    }

    public String toString(Node<T> node, int padding) {
        String out = "";

        if (node == null)
            return "";
     
        out += this.toString(node.right, padding + 1);
     
        out += "\n";
        out += new String(new char[padding]).replace("\0", "    ");
        out += node.value;
        out += "\n";
     
        out += this.toString(node.left, padding + 1);

        return out;
    }
}

class AVL<T extends Comparable<T>> {
    Node<T> root;

    AVL() {
    }

    void insert(T store) {
        if (root == null) {
            root = new Node<T>(store);
        } else {
            root = root.insert(store);
        }
    }

    Node<T> find(T value) {
        Node<T> cur = root;

        while (cur != null) {
            if (cur.value.compareTo(value) == 0) {
                break;
            }

            cur = cur.value.compareTo(value) > 0 ? cur.left : cur.right;
        }

        return cur;
    }

    int rank(T value) {
        Node<T> cur = root;
        int rank = 0;

        while (cur != null) {
            if (cur.value.compareTo(value) > 0) {
                cur = cur.left;
            } else if (cur.value.compareTo(value) < 0) {
                rank += (cur.left == null ? 0 : cur.left.weight()) + 1;
                cur = cur.right;
            }
            else {
                rank += cur.left == null ? 0 : cur.left.weight();
                break;
            }
        }
        return rank;
    }

    void delete(T value) { // do i need this?
        delete(value, root);
    }

    private Node<T> delete(T value, Node<T> cur) {
        // todo: refactor, very jank
        if (cur.value.compareTo(value) > 0) {
            cur.left = delete(value, cur.left);
        } else if (cur.value.compareTo(value) < 0) {
            cur.right = delete(value, cur.right);
        } else {
            if (cur.left == null && cur.right == null) {
                ;
            } else if (cur.left == null) {
                cur = cur.right;
            } else if (cur.right == null) {
                cur  = cur.left;
            } else {
                Node<T> temp = cur.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                cur.value = temp.value;
                cur.right = delete(value, cur.right);
            }
        }

        return cur.balance();
    }

    @Override
    public String toString() {
        return root.toString();
    }

}
public class Node<Key extends Comparable<Key>, Value> {
    Key key;      // Данные в качестве ключа
    Value value;   // другие данные
    Node left;
    Node right;
    Node parent;
    boolean color;

    public Node (){
    }

}

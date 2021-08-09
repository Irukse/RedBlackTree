import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree <Key extends Comparable<Key>, Value> {

    public static void main(String[] args) {
        RedBlackTree  rbrTree = new RedBlackTree ();
        rbrTree.insert(20, 4);
        rbrTree.insert(30, 5);
        rbrTree.insert(40, 6);
        rbrTree.insert(50, 6);
        rbrTree.insert(60, 6);
        rbrTree.insert(70, 6);
        rbrTree.insert(80, 6);
        rbrTree.insert(90, 6);
        rbrTree.insert(100, 6);
        rbrTree.insert(110, 6);
        rbrTree.insert(120, 6);


        System.out.println(rbrTree.show(rbrTree));
    }

    private Node root;   // корень дерева
    private int size=0;

     // находим деда
    public Node grandparent(Node node ){
        if(node!=null && node.parent.parent !=null){
            return node.parent.parent;
        }
        else return null;
    }
    // Находим корень дерева
    public Node searchRoot (Node node){
        while (node.parent!=null){
            node=node.parent;
        }
        root=node;
        return  root;
    }

    // находим дядю
    public Node uncle (Node node ){
        Node g= grandparent(node);
        // No grandparent means no uncle
        if(g==null){
            return null;
        }
        if(node.parent==g.left){
            return g.right;
        }
        else return g.left;
    }
      // реализуем правый разворот
     public void  rotate_right (Node node){
        Node pivot = node.left;
        pivot.parent = node.parent;
        if( node.parent !=null){
            if(node.parent.left==node){
                node.parent.left=pivot;
            }
          else
         node.parent.right=pivot;
        }

        node.parent=pivot;
        node.left=pivot.right;
        if(pivot.right!=null){
            pivot.right.parent=node;
        }
        node.parent=pivot;
        pivot.color=node.color;
        pivot.right=node;
        node.color=false;
     }
    // реализуем левый разворот

    public void  rotate_left (Node node){
        Node pivot = node.right;
        pivot.parent = node.parent;

        if( node.parent !=null){
            if(node.parent.left==node){
                node.parent.right=pivot;
            }
            else
            node.parent.right=pivot;
        }
        node.parent=pivot;
        node.right=pivot.left;
        if(pivot.left!=null){
            pivot.left.parent=node;
        }
        node.parent=pivot;
        pivot.color=node.color;
        pivot.left=node;
        node.color=false;
    }

  public void insert (Key key, Value value){
     Node newNode = new Node(); // Создание нового узла
     if(size==0){
         root=newNode;
         root.key=key;
         root.value=value;
         root.parent=null;
         root.color=true;

         size++;
     } else {
         Node current = root;  // создаем указатель, ссылаем его на root
         Node parent;   // создаем еще один указатель ( на родителя)
         Node counter;
         while (true) {
          //   counter = current;

             int cmp = key.compareTo((Key) root.key);
             if (cmp<0) {
                 // двигаться налево?
                 parent=current;
                 counter=current;
                 current = current.left;
                 if (current == null) {
                     // если достигнут конец цепочки, вставить слева
                     counter.left= newNode;
                     newNode.key=key;
                     newNode.value=value;
                     newNode.color=false;
                     newNode.parent=parent;
                     balanceAfterInsert(newNode);

                     return;
                 }
             } else {
                 parent=current;
                 counter=current;
                 current = current.right;
                 if (current == null)  // Если достигнут конец цепочки,
                 {                 // вставить справа
                     counter.right = newNode;
                     newNode.key=key;
                     newNode.value=value;
                     newNode.color=false;
                     newNode.parent=parent;
                     balanceAfterInsert(newNode);

                     return;
                 }

                 }



         }

     }



    }

    private void balanceAfterInsert(Node node) {
        //если вставляем корень или родитель черный, то балансировка не нужна
        if (node == null || node == this.root || node.parent.color==true || node.parent == null) {
            return;
        }
        //если брат дедушки красный
        Node u = uncle(node);
        Node g = grandparent(node);
        if(u!=null && u.color==false){
            //меняем цвета: родителя, брата дедушики, дедушки
            swapColor(node.parent);
            swapColor(u);
            swapColor(g);
            searchRoot(node);
            root.color = true;
            balanceAfterInsert(g);
        }
        else if(g.left==node.parent){
            //если вставка в левую ветвь дедушки
            if (node.parent.right == node) {
                //если вставка в правую вевь родителя
               // node = node.parent;
                //поворачиваем налево
                rotate_left(node.parent);
            }

            node.parent.color=true;
            g.color=false;
            rotate_right(g);
            searchRoot(node);


        }
        else if(g.right==node.parent){
            if (node.parent.left == node) {
               // node = node.parent;
                rotate_right(node.parent);
            }

            node.parent.color=true;
            g.color=true;
            rotate_left(g);
            searchRoot(node);

        }

    }

    public static void swapColor(Node node) {
        if (node == null)
            return;

        node.color = !node.color;
    }


    public boolean show (RedBlackTree tree) {
        Queue<Node> queue = new LinkedList<>();
        // node - корневой узел, с которого начинаем проходить по дереву
        searchRoot (root);
        queue.add(root);

        while (!queue.isEmpty()) {
            //берет элементы из вершины списка
            root =  queue.remove();
            System.out.println(root.key );
            System.out.println(root.color);
            if(root.right!=null){
                queue.add(root.right);
            }
            if(root.left !=null){
                queue.add(root.left);
            }
        }
        return true;
    }

}

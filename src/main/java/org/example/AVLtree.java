package org.example;

import java.util.Arrays;
import java.util.PriorityQueue;

public class AVLtree {
    Node root;

    AVLtree(){}

    AVLtree(int[] keys){
        buildTree(keys);
    }

    void buildTree (int[] keys) {
        Arrays.sort(keys);
        this.root = createNode(keys);
    }

    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private void updateHeight(Node n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }
    }

    public Node createNode(int[] keys){
        if (keys.length == 0){
            return null;
        }
        int med = keys.length / 2;
        Node node = new Node(keys[med]);
        int[] left = Arrays.copyOfRange(keys, 0, med);
        int[] right = Arrays.copyOfRange(keys, med+1, keys.length);
        node.left = createNode(left);
        node.right = createNode(right);
        node.height = (int) Math.round(Math.log(keys.length)/Math.log(2));
        return node;
    }

    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Выполняем поворот
        x.right = y;
        y.left = T2;

        // Обновляем высоты
        updateHeight(y); // y теперь дочерний, его высота обновляется первым
        updateHeight(x); // x теперь родитель, его высота обновляется вторым

        return x; // Возвращаем новый корень поддерева
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Выполняем поворот
        y.left = x;
        x.right = T2;

        // Обновляем высоты
        updateHeight(x); // x теперь дочерний, его высота обновляется первым
        updateHeight(y); // y теперь родитель, его высота обновляется вторым

        return y; // Возвращаем новый корень поддерева
    }

    private Node insert(int key, Node node) {
        // Если узел пустой, создаем новый узел
        if (node == null) {
            return new Node(key);
        }

        // Вставляем ключ в левое или правое поддерево
        if (key < node.key) {
            node.left = insert(key, node.left);
        } else if (key > node.key) {
            node.right = insert(key, node.right);
        } else {
            // Если ключ уже существует, ничего не делаем
            return node;
        }

        // Обновляем высоту узла
        updateHeight(node);

        // Проверяем балансировку узла
        return balance(node);
    }

    private Node balance(Node node) {
        // Проверяем балансировку узла
        int bf = getBalance(node);

        // Лево-Левый случай (LL)
        if (bf > 1 && getBalance(node.left) >= 0){
            //System.out.println("rro");
            return rightRotate(node);
        }

        // Право-Правый случай (RR)
        if (bf < -1 && getBalance(node.right) <= 0){
            //System.out.println("lro");
            return leftRotate(node);
        }

        // Лево-Правый случай (LR)
        if (bf > 1 && getBalance(node.left) < 0){
            node.left = leftRotate(node.left); // Сначала левый поворот на левом потомке
            //System.out.println("lrro");
            return rightRotate(node);          // Затем правый поворот на текущем узле
        }

        // Право-Левый случай (RL)
        if (bf < -1 && getBalance(node.right) > 0){
            node.right = rightRotate(node.right); // Сначала правый поворот на правом потомке
            //System.out.println("rlro");
            return leftRotate(node);              // Затем левый поворот на текущем узле
        }

        return node; // Возвращаем (возможно) обновленный узел
    }

    public void insert(int key) {  this.root = insert(key, this.root);    }

    public Node search (int key, Node node){
        if (node == null) {
            return null;
        }
        else if (node.key == key){
            return node;
        }
        else if (key > node.key) {
            return search(key, node.right);
        }
        else {
            return search(key, node.left);
        }
    }

    public void delete(int key){
        this.root = delete(key, this.root);
    }

    Node delete (int key, Node node){
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = delete(key, node.left);
        } else if (key > node.key) {
            node.right = delete(key, node.right);
        } else {
            node = replace(node);
        }

        updateHeight(node);
        return balance(node);
    }

    Node replace(Node node){
        if (node == null){
            return null;
        }
        if (node.left != null){
            node.key = node.left.key;
            if (node.left.right == null & node.left.left == null){
                node.left = null;
                return node;
            }
            else{
                return replace(node.left);
            }
        }
        else if (node.right != null ){
            node.key = node.right.key;
            if (node.right.right == null & node.right.left == null){
                node.right = null;
                return node;
            }
            else{
                return replace(node.right);
            }
        }
        else {
            return null;
        }
    }
}

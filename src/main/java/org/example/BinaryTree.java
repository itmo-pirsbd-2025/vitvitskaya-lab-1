package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class BinaryTree {
    Node root;
    ArrayList<Node> stack;
    BinaryTree(){stack = new ArrayList<>();}

    BinaryTree(int[] keys){
        buildTree(keys);
        stack = new ArrayList<>();
    }

    void buildTree (int[] keys) {
        Arrays.sort(keys);
        this.root = createNode(keys);
    }

    public Node createNode(int[] keys){
        if (keys.length == 0){
            return null;
        }
        int med = keys.length / 2;
        Node node = new Node(keys[med]);
        if (keys.length == 1){
            return node;
        }
        else {
            int[] left = Arrays.copyOfRange(keys, 0, med);
            int[] right = Arrays.copyOfRange(keys, med+1, keys.length);
            node.left = createNode(left);
            node.right = createNode(right);
            return node;
        }
    }

    public void insert(int key){
        this.root = insert(key, this.root);
    }

    private Node insert(int key, Node node) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insert(key, node.left);
        } else if (key > node.key) {
            node.right = insert(key, node.right);
        }
        return node;
    }

    void insertC(int key) {
        Node current = this.root;
        if (current == null) {
            this.root = new Node(key); // Если дерево пустое, устанавливаем новый узел как корень
            return;
        }

        while (true) {
            //parent = current; // Сохраняем текущий узел как родитель
            if (key < current.key) {
                if (current.left == null){
                    current.left = new Node(key);
                    return;
                }
                else {
                    current = current.left; // Переход к левому дочернему узлу
                }
            } else if (key > current.key) {
                if (current.right == null){
                    current.right = new Node(key);
                    return;
                }
                else {
                    current = current.right; // Переход к правому дочернему узлу
                }
            }
            else{
                return;
            }
        }
    }

    public void insertB(int key) {
        if (this.root == null) {
            this.root = new Node(key);
            return;
        }

        Node current = this.root;

        while (true) {
            if (key < current.key) {
                if(current.left == null){
                    current.left = new Node(key);
                    return;
                } else {
                    current = current.left;
                }
            }
            else if (key > current.key) {
                if (current.right != null) {
                    current.right = new Node(key);
                    return;
                }
                else {
                    current = current.right;
                }
            }
        }
    }

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

    public Node searchC(int key) {
        if (this.root == null) {
            return null;
        }

        Node current = this.root;

        while (true) {
            if (current == null) {
                return null;
            }
            else if (current.key == key){
                return current;
            }
            if (key < current.key) {
                current = current.left;
            }
            else {
                current = current.right;
            }
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
        return node;
    }
    public void deleteC(int key) {
        if (this.root == null) {
            this.root = new Node(key);
            return;
        }

        Node current = this.root;
        Node parent = null;

        while (true) {
            if (current == null) {
                return;
            }
            parent = current;
            if (key < current.key) {
                current = current.left;
                if (current.key == key) {
                    parent.left = replace(current);
                    return;
                }
            }
            else {
                current = current.right;
                if (current.key == key) {
                    parent.right = replace(current);
                    return;
                }
            }
        }
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

    void inOrder(Node node, ArrayList<Integer> res){
        if (node == null){
            return;
        }

        if (node.left != null){
            res.add(node.left.key);
            inOrder(node.left, res);
        }
        res.add(node.key);
        if (node.right != null) {
            res.add(node.right.key);
            inOrder(node.right, res);
        }
    }
}

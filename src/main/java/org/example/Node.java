package org.example;

public class Node {
    int height;
    Node left;
    Node right;
    int key;
    boolean isProcessed;
    public Node(int key){
        this.key = key;
        this.height = 0;
    }
    public void setIsProcessed(boolean flg){this.isProcessed = flg;}
}

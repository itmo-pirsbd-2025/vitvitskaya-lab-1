package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int n = 10000;
        BinaryTree tree;
        int[] keys;
        Random random;
        int key;

        random = new Random(42); // Фиксированный seed для воспроизводимости
        keys = random.ints(n, 0, n * 3).distinct().toArray();
        tree = new BinaryTree(keys);
        key = random.nextInt(n * 3);
        //Arrays.sort(keys);
//        for (int k : keys) {
//            tree.insertC(k);
//        }
        tree.insertC(key);
        System.out.println(tree.searchC(key));
    }
}
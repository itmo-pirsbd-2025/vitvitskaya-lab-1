package org.example;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BinaryTreeBenchmark {
    @Param({"1000", "10000"})
    int n;
    private BinaryTree tree;
    int[] keys;
    private Random random;
    int key;
    int skey;
    int dkey;

    @Setup(Level.Iteration)
    public void setUp() {
        random = new Random(42); // Фиксированный seed для воспроизводимости
        keys = random.ints(n, 0, n * 3).distinct().toArray();
        this.tree = new BinaryTree(keys);
        key = random.nextInt(n*5); /*ключ, которого точно нет в дереве*/
        skey = random.nextInt(n*2); /*ключ, который точно есть в дереве*/
        dkey = random.nextInt(n); /*ключ, который точно есть в дереве*/
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void insertC() {
        tree.insertC(key);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void insert() {
        tree.insert(key);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void search(Blackhole bh) {
        bh.consume(tree.search(skey, tree.root));
    }


    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void delete(Blackhole bh) {
        tree.delete(dkey);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BinaryTreeBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}

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
public class WorstCaseBenchmark {
    @Param({"1000", "100000"})
    int n;
    private BinaryTree bintree;
    private AVLtree avltree;
    int[] keys;
    private Random random;
    int skey;

    @Setup(Level.Iteration)
    public void setUp() {
        random = new Random(42); // Фиксированный seed для воспроизводимости
        keys = random.ints(n, 0, n * 3).distinct().toArray();
        this.bintree = new BinaryTree();
        this.avltree = new AVLtree();
        Arrays.sort(keys);
        for(int k : keys){
            bintree.insertC(k);
            avltree.insert(k);
        }
        skey = random.nextInt(n*2);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void searchWorstCaseBin(Blackhole bh) {
        bh.consume(bintree.searchC(skey));
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Fork(1)
    @Benchmark
    public void searchWorstCaseAVL(Blackhole bh) {
        bh.consume(avltree.search(skey, avltree.root));
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(WorstCaseBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}

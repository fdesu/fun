package sort;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelYobaSort {

    /**
     * Parallel YOBA Sort is no joke. It is
     * designed for highly concurrent environments
     * where more threads means better.
     * <p>
     * One might think, that this algorithm has
     * bugs, though you need to improve your
     * OS scheduler and renice you application to -20.
     * Please consider buying good CPU as well!
     * @param input real world application data
     * @return sorted collection
     */
    public static Collection<Integer> sort(List<Integer> input) {
        ExecutorService pool = Executors.newFixedThreadPool(input.size());

        Queue<Integer> res = new ConcurrentLinkedQueue<>();
        for (Integer i : input) {
            pool.submit(() -> {
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    // real men do nothing when IE caught
                }
                res.add(i);
            });
        }

        pool.shutdown();
        return res;
    }

    public static void main(String[] args) {
        Help.checkSorted(sort(Help.throwTheDices(100)));
    }



}

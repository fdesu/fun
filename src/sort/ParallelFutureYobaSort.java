package sort;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFutureYobaSort {

    /**
     * It is no joke... well... it works... sometimes lul
     *
     * @param input real world application data
     * @return sorted collection
     *
     * @throws InterruptedException in your face! if you dare to interrupt yoba sort
     */
    private static Collection<Integer> sort(List<Integer> input) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(input.size());

        Queue<Integer> res = new ConcurrentLinkedQueue<>();

        CompletableFuture.allOf(
            input.stream()
                .map(integer ->
                         CompletableFuture.supplyAsync(() -> integer, pool)
                             .thenApply(ParallelFutureYobaSort::peekAndWait)
                             .thenAccept(res::add)
                ).toArray(CompletableFuture[]::new)
        ).get();

        pool.shutdown();
        return res;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Collection<Integer> sort = sort(Help.throwTheDices(10));
        System.out.println(sort);
        Help.checkSorted(sort);
    }

    private static int peekAndWait(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Dare to interrupt yoba sort?");
        }
        return i;
    }

}

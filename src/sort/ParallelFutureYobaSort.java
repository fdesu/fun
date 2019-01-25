package sort;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ParallelFutureYobaSort {

    /**
     * It is no joke... well... it works... sometimes lul
     *
     * @param input
     *
     * @return
     *
     * @throws InterruptedException
     */
    private static Collection<Integer> sort(List<Integer> input) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(input.size());

        Queue<Integer> res = new ConcurrentLinkedQueue<>();

        List<CompletableFuture<Void>> tasks = input.stream()
            .map(integer ->
                     CompletableFuture.supplyAsync(() -> integer, pool)
                         .thenApply(i -> {
                             try {
                                 Thread.sleep(i);
                             } catch (InterruptedException e) {
                                 throw new IllegalStateException("Dare to interrupt yoba sort?");
                             }
                             return i;
                         })
                         .thenAccept(res::add)
            ).collect(Collectors.toList());

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).get();
        pool.shutdown();
        return res;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Collection<Integer> sort = sort(Help.throwTheDices(10));
        System.out.println(sort);
        Help.checkSorted(sort);
    }

}

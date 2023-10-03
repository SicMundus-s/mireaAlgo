package task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class MaxElementSearchExample {
    private static final int ARRAY_SIZE = 10000;
    private static final int THREAD_POOL_SIZE = 4;

    public static void task1() throws InterruptedException, ExecutionException {
        int[] array = new int[ARRAY_SIZE];

        // 0. Последовательно
        long startTime = System.currentTimeMillis();
        int maxSequential = findMaxSequential(array);
        long endTime = System.currentTimeMillis();
        System.out.println("Последовательно Max: " + maxSequential + ", Время: " + (endTime - startTime) + " ms");

        // 1. С использованием многопоточности
        startTime = System.currentTimeMillis();
        int maxMultiThreaded = findMaxMultiThreaded(array);
        endTime = System.currentTimeMillis();
        System.out.println("Многопоточно Max: " + maxMultiThreaded + ", Время: " + (endTime - startTime) + " ms");

        // 2. С использованием ForkJoin
        startTime = System.currentTimeMillis();
        int maxForkJoin = findMaxForkJoin(array);
        endTime = System.currentTimeMillis();
        System.out.println("ForkJoin Max: " + maxForkJoin + ", Время: " + (endTime - startTime) + " ms");
    }

    private static int findMaxSequential(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            max = Math.max(max, num);
            sleep(1);
        }
        return max;
    }

    private static int findMaxMultiThreaded(int[] array) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            int startIndex = i * (ARRAY_SIZE / THREAD_POOL_SIZE);
            int endIndex = (i + 1) * (ARRAY_SIZE / THREAD_POOL_SIZE);
            Future<Integer> future = executorService.submit(() -> findMaxSubArray(array, startIndex, endIndex));
            futures.add(future);
        }

        int max = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            max = Math.max(max, future.get());
        }

        executorService.shutdown();
        return max;
    }

    private static int findMaxSubArray(int[] array, int startIndex, int endIndex) {
        int max = Integer.MIN_VALUE;
        for (int i = startIndex; i < endIndex; i++) {
            max = Math.max(max, array[i]);
            sleep(1);
        }
        return max;
    }

    private static int findMaxForkJoin(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_POOL_SIZE);
        int max = forkJoinPool.invoke(new MaxTask(array, 0, ARRAY_SIZE));
        forkJoinPool.shutdown();
        return max;
    }

    private static class MaxTask extends RecursiveTask<Integer> {
        private final int[] array;
        private final int startIndex;
        private final int endIndex;

        MaxTask(int[] array, int startIndex, int endIndex) {
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        protected Integer compute() {
            if (endIndex - startIndex <= ARRAY_SIZE / THREAD_POOL_SIZE) {
                return findMaxSubArray(array, startIndex, endIndex);
            } else {
                int mid = (startIndex + endIndex) / 2;
                MaxTask leftTask = new MaxTask(array, startIndex, mid);
                MaxTask rightTask = new MaxTask(array, mid, endIndex);

                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();

                return Math.max(leftResult, rightResult);
            }
        }
    }

    // Не хочу везде try catch писать
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

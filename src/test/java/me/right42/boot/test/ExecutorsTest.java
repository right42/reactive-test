package me.right42.boot.test;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorsTest {

    @Test
    void submit() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<?> submit = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " hi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        submit.get();
        System.out.println("task end");

        close(executorService);
    }

    @Test
    void callable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = getFixedThreadPool(1);

        Callable<Integer> callable = () -> {
            TimeUnit.SECONDS.sleep(3);
            return 1234;
        };

        Future<Integer> submit = executorService.submit(callable);
        System.out.println("get before isDone : " + submit.isDone());
        Integer integer = submit.get();
        System.out.println("get after isDone : " + submit.isDone());

        assertThat(integer).isEqualTo(1234);

        assertThrows(TimeoutException.class, () -> {
            Future<Integer> submit2 = executorService.submit(callable);

            submit2.get(1, TimeUnit.SECONDS);
        });
    }

    @Test
    void invokeAll() throws InterruptedException {
        ExecutorService fixedThreadPool = getFixedThreadPool(2);

        List<Callable<Integer>> callables = Arrays.asList(
            () -> {
                System.out.println("task1");
                TimeUnit.SECONDS.sleep(1);
                return 1234;
            },
            () -> {
                System.out.println("task2");
                TimeUnit.SECONDS.sleep(2);
                return 4567;
            }
        );

        fixedThreadPool.invokeAll(callables)
            .stream()
            .map(integerFuture -> {
                try {
                    return integerFuture.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            })
            .forEach(System.out::println);
        ;
        ;
    }


    private ExecutorService getFixedThreadPool(int size) {
        return Executors.newFixedThreadPool(size);
    }


    private void close(ExecutorService executorService){

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(!executorService.isTerminated()) {
                System.err.println("not terminated executor");
            }

            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

}

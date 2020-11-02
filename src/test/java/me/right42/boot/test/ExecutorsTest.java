package me.right42.boot.test;

import org.junit.jupiter.api.Test;

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
        ExecutorService executorService = Executors.newFixedThreadPool(1);

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

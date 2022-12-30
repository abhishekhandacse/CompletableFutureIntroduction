/*
Completable Future-:
Perform possible asynchronous (non-blocking) computations and trigger dependant computations
which could also be asynchronous.
Credits-: DefogTech Deepak V
 */

import java.util.Random;
import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(10);
        Future<Integer> future=service.submit(new Task());
        try{
            Integer result= future.get();
            System.out.println("Result from the task is"+result);
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

    }

    static class Task implements Callable<Integer> {
        public Integer call(){
            return new Random().nextInt();
        }
    }
}
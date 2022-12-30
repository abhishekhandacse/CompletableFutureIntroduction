/*
Credits-: DefogTech Deepak V
Assume Sequential Process where the pipeline is like,
(For all the orders)
1) Fetch Order
2) Enrich Order
3) Payment
4) Dispatch
5) Send Email
Completable Futures got introduced in Java 8
*/
import java.util.concurrent.*;

public class CompletableFutureExample {
    static class Order{
        int orderId;
        String orderItem;
        public Order(int orderId, String orderItem) {
            this.orderId = orderId;
            this.orderItem = orderItem;
        }
    }
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(10);
        for(int i=0;i<100;i++){
            CompletableFuture
                    .supplyAsync(()->getOrderTask())
                    .thenApply(o->enrichTask(o))
                    .thenApply((order)->performPayment(order))
                    .thenApply((order)->dispatch(order))
                    .thenAccept((order)->sendEmailTask(order));
        }


        service.shutdown();
    }

    public static Order sendEmailTask(Order order) {
            System.out.println("Sending Email Update");
            return order;
    }

    public static Order dispatch(Order order) {
            System.out.println("Doing Dispatch");
            return order;
    }

    public static Order performPayment(Order order) {
            System.out.println("Performing Payment");
            return order;
    }
    public static Order enrichTask (Order order) {
            System.out.println("Doing Enrichment");
            return order;
    }

    public static  Order getOrderTask(){
            System.out.println("Placing Order");
            return new Order(1,"Apple");
    }

}

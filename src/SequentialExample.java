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
import java.util.Random;
import java.util.concurrent.*;

public class SequentialExample {
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
        try{
            Future<Order> future=service.submit(new getOrderTask());
            Order order=future.get();//blocking

            Future<Order> future1=service.submit(new enrichTask(order));
            order=future1.get();//blocking

            Future<Order> future2=service.submit(new performPayment(order));
            order=future2.get();//blocking

            Future<Order> future3=service.submit(new dispatch(order));
            order=future3.get();//blocking

            Future<Order> future4=service.submit(new sendEmailTask(order));
            order=future4.get();//blocking

        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        service.shutdown();
    }

    static class sendEmailTask implements Callable<Order> {
        private final Order order;
        public sendEmailTask(Order order){
            this.order=order;
        }

        @Override
        public Order call() throws Exception {
            System.out.println("Sending Email Update");
            return order;
        }
    }

    static class dispatch implements Callable<Order> {
        private final Order order;
        public dispatch(Order order){
            this.order=order;
        }

        @Override
        public Order call() throws Exception {
            System.out.println("Doing Dispatch");
            return order;
        }
    }

    static class performPayment implements Callable<Order> {
        private final Order order;
        public performPayment(Order order){
            this.order=order;
        }

        @Override
        public Order call() throws Exception {
            System.out.println("Performing Payment");
            return order;
        }
    }
    static class enrichTask implements Callable<Order> {

        private final Order order;
        public enrichTask(Order order){
            this.order=order;
        }

        @Override
        public Order call() throws Exception {
            System.out.println("Doing Enrichment");
            return order;
        }
    }
    static class getOrderTask implements Callable<Order> {

        @Override
        public Order call(){
            System.out.println("Placing Order");
            return new Order(1,"Apple");
        }
    }
}

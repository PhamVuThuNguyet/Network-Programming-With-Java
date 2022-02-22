package ThreadAndMultiThreading;

public class Ex2 extends Thread{
    public void run(){
        for (int i = 0; i < 10; i++) {
            printMsg();
        }
    }

    public void printMsg(){
        Thread thread = Thread.currentThread();
        String threadName = thread.getName();
        System.out.println("Thread: " + threadName);
    }

    public static void main(String[] args) {
        Ex2 ex2 = new Ex2();
        ex2.setName("New Thread 1");

        System.out.println("Before Start, Thread status: " + ex2.isAlive());
        ex2.start();
        System.out.println("After Start, Thread status: " + ex2.isAlive());

        for (int i = 0; i < 10; i++) {
            ex2.printMsg();
        }

        System.out.println("End main, Thread status: " + ex2.isAlive());
    }
}

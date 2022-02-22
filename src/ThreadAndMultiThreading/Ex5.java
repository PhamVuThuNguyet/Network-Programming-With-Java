package ThreadAndMultiThreading;

class MyThread implements Runnable {
    String tName;
    Thread t;

    MyThread(String threadName) {
        tName = threadName;
        t = new Thread(this, tName);
        t.start();
    }

    public void run() {
        try {
            System.out.println("Thread: " + tName);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Exception: Thread " + tName + " interrupted");
        }
        System.out.println("Terminating thread: " + tName);
    }
}

public class Ex5 {
    public static void main(String[] args) {
        new MyThread("1");
        new MyThread("2");
        new MyThread("3");
        new MyThread("4");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Exception: Thread main interrupted.");
        }
        System.out.println("Terminating thread: main thread.");

    }
}

package ThreadAndMultiThreading;

class SimpleThread extends Thread {
    public SimpleThread(String str) {
        super(str);
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + " " + getName());
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hoan thanh tien trinh: " + getName());
    }
}


public class Ex3 {
    public static void main(String[] args) {
        new SimpleThread("Ha Noi").start();
        new SimpleThread("Da Nang").start();
        new SimpleThread("Sai Gon").start();
    }

}

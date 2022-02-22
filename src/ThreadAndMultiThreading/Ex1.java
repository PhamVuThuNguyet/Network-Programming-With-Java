package ThreadAndMultiThreading;

public class Ex1 extends Thread{
    public void run(){
        System.out.println("New Thread");
    }

    public static void main(String[] args) {
        Ex1 ex1 = new Ex1();
        ex1.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Main Thread");
        }
    }
}

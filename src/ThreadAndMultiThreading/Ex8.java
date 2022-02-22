package ThreadAndMultiThreading;

class Parentheses {
    void display(String s) {
        System.out.print("(" + s);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(")");
    }
}

class ParenthesesWithSync {
    synchronized void display(String s) {
        System.out.print("(" + s);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(")");
    }
}

public class Ex8 implements Runnable {
    String s1;
    Parentheses p1;
    ParenthesesWithSync parenthesesWithSync;
    Thread t;

    public Ex8(Parentheses p2, String s2) {
        p1 = p2;
        s1 = s2;
        t = new Thread(this);
        t.start();
    }

    public Ex8(ParenthesesWithSync p2, String s2) {
        parenthesesWithSync = p2;
        s1 = s2;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        if (p1 != null) {
            p1.display(s1);
        }
        if (parenthesesWithSync != null) {
            parenthesesWithSync.display(s1);
        }
    }

    public static void main(String[] args) {
        Parentheses p3 = new Parentheses();
        Ex8 name1 = new Ex8(p3, "Bob");
        Ex8 name2 = new Ex8(p3, "Mary");
        try {
            name1.t.join();
            name2.t.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        ParenthesesWithSync p4 = new ParenthesesWithSync();
        Ex8 name3 = new Ex8(p4, "BobSync");
        Ex8 name4 = new Ex8(p4, "MarySync");
        try {
            name3.t.join();
            name4.t.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

}

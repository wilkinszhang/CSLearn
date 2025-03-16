package com.wilkinszhang;

public class ABCPrinter {
    private int State=0;
    private final int rounds=10;

    public void printer(char letter,int targetState){
        for(int i=0;i<rounds;i++){
            synchronized (this){
                while(State%3!=targetState){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print(letter);
                State++;
                notifyAll();
            }
        }
    }
    public static void main(String[]args){
        ABCPrinter abcprinter=new ABCPrinter();
        Thread thread1=new Thread(()->abcprinter.printer('A',0),"thread 1");
        Thread thread2=new Thread(()->abcprinter.printer('B',1),"thread 2");
        Thread thread3=new Thread(()->abcprinter.printer('C',2),"thread 3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

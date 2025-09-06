package com.wilkinszhang;

// 优雅终止线程
public class Proxy {
    /*
     设置自己的线程终止标志位，而不是用线程包自带的线程的中断状态，即Thread.currentThread().isInterrupted()。
     原因：可能在线程run方法中调用第三方提供的方法，我们不能保证第三方类库正确处理了线程中断异常，如第三方类库捕获到Thread.sleep()方法抛出的
     中断异常后，没有重新设置线程的中断状态，那么就会导致线程不能正常终止。
    */
    volatile boolean terminated=false;
    boolean started=false;
    Thread rptThread;
    synchronized void start(){
        if(started){
            return;
        }
        started=true;
        terminated=false;
        rptThread=new Thread(()->{
            while(!terminated){
                report();
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
            started=false;
        });
        rptThread.start();
    }

    private void report() {

    }

    synchronized void stop(){
        terminated=true;
        rptThread.interrupt();
    }
}

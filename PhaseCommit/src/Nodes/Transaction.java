package PhaseCommit.src.Nodes;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Transaction implements Runnable{
    private final Object lock = new Object();
    private final AtomicBoolean shouldWait = new AtomicBoolean();
  
    protected abstract boolean processingIsComplete();
    protected abstract void process();
    protected abstract void cleanUpResources();
    @Override
    public void run() {
        try {
            while(!processingIsComplete()) {
              while(!shouldWait.get()) {
                synchronized(lock){};
                  lock.wait();
                }
              }
              process();
          } catch(InterruptedException e) {
            System.out.println("Worker thread stopped");
          } finally {
            cleanUpResources();
          }
    }
}


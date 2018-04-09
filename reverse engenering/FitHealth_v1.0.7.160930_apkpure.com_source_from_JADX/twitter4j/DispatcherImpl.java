package twitter4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import twitter4j.conf.Configuration;

final class DispatcherImpl implements Dispatcher {
    private static final long SHUTDOWN_TIME = 5000;
    private static final Logger logger = Logger.getLogger(DispatcherImpl.class);
    private final ExecutorService executorService;

    class C14542 extends Thread {
        C14542() {
        }

        public void run() {
            DispatcherImpl.this.executorService.shutdown();
        }
    }

    public DispatcherImpl(final Configuration conf) {
        this.executorService = Executors.newFixedThreadPool(conf.getAsyncNumThreads(), new ThreadFactory() {
            int count = 0;

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                Object[] objArr = new Object[1];
                int i = this.count;
                this.count = i + 1;
                objArr[0] = Integer.valueOf(i);
                thread.setName(String.format("Twitter4J Async Dispatcher[%d]", objArr));
                thread.setDaemon(conf.isDaemonEnabled());
                return thread;
            }
        });
        Runtime.getRuntime().addShutdownHook(new C14542());
    }

    public synchronized void invokeLater(Runnable task) {
        this.executorService.execute(task);
    }

    public synchronized void shutdown() {
        this.executorService.shutdown();
        try {
            if (!this.executorService.awaitTermination(SHUTDOWN_TIME, TimeUnit.MILLISECONDS)) {
                this.executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.warn(e.getMessage());
        }
    }
}

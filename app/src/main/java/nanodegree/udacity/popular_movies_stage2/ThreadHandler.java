package nanodegree.udacity.popular_movies_stage2;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadHandler {
    private static final Object LOCK = new Object();
    private static ThreadHandler executorInstance;
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;


    private ThreadHandler(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static ThreadHandler getExecutorInstance() {
        if (executorInstance == null) {
            synchronized (LOCK) {
                executorInstance = new ThreadHandler(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return executorInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler= new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}

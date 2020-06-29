package com.qi7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThreadPool implements RunnableInter {
    private static Integer numberOfThread;
    private static ThreadPool threadPool;
    private ThreadWork[] threadWorks;
    private List<Runnable> runnableList = new ArrayList<>();
    private int finishedTask;

    private ThreadPool(){
        this(5);
    }
    private ThreadPool(Integer numberOfThread){
        numberOfThread = numberOfThread;
        threadWorks = new ThreadWork[numberOfThread];

        for (int i = 0; i < threadWorks.length; i++) {
            threadWorks[i] = new ThreadWork();
            threadWorks[i].start(); //开启线程池中的线程
        }
    }

    public static ThreadPool getThreadPoll(){
        return Objects.requireNonNullElseGet(threadPool, ThreadPool::new);
    }

    public static ThreadPool getThreadPool(Integer numberOfThread){
        if (numberOfThread <= 0)
            throw new RuntimeException("numberOfThread can't allow empty");

        return Objects.requireNonNullElseGet(threadPool, ()->{return new ThreadPool(numberOfThread);});
    }

    public void execute(Runnable runnable){
        synchronized (runnableList){
            runnableList.add(runnable);
            runnableList.notify();
        }
    }

    public void execute(Runnable[] runnables){
        synchronized (runnableList){
            for (Runnable runnable : runnables)
                runnableList.add(runnable);
            runnableList.notify();

        }
    }

    public void execute(List<Runnable> runnables){
        synchronized (runnableList){
            for (Runnable runnable : runnables)
                runnableList.add(runnable);
            runnableList.notify();
        }
    }

    public void destroy(){

        //如果线程任务还没完成先等待任务完成
        while (!runnableList.isEmpty()){
            try {
                Thread.sleep(50);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < threadWorks.length; i++) {
            threadWorks[i].stopWorker();
            threadWorks[i] = null;
        }
        threadPool = null;
        runnableList.clear();
    }

    //返回线程工作的个数
    public int getWorkThreadNumber(){
        return numberOfThread;
    }

    public int getFinishedTaskNumber(){
        return finishedTask;
    }

    public int getWaitTaskNumber(){
        return runnableList.size();
    }

    @Override
    public void addRunnable(List<Runnable> list) {
        execute(list);
    }

    @Override
    public void addRunnable(Runnable[] runnables) {
        execute(runnables);
    }

    @Override
    public void addRunnable(Runnable runnable) {
        execute(runnable);
    }

    private class ThreadWork extends Thread{
        private boolean isRunning = true;

        @Override
        public void run() {
            Runnable runnable = null;
            while (isRunning){
                synchronized (runnableList){
                    while (isRunning && runnableList.isEmpty()){
                        try {
                            runnableList.wait(50);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    runnable = runnableList.remove(0);
                }

                if (runnable != null)
                    runnable.run();
                finishedTask++;
                runnable = null;
            }

        }


        public void stopWorker(){
            isRunning = false;
        }
    }



}

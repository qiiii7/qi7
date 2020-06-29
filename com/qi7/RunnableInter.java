package com.qi7;

import java.util.List;

public interface RunnableInter {
    void addRunnable(List<Runnable> list);
    void addRunnable(Runnable[] runnables);
    void addRunnable(Runnable runnable);
}

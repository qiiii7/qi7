package com.qi7;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class FindDateThread {
    private RunnableInter runnableInter;
    private static FindDateThread findDateThread;
    private ThreadWorker threadWorker;

    private List<HouseIdAndDate.data> list;

    private FindDateThread(RunnableInter runnableInter){
        threadWorker = new ThreadWorker();
        list = HouseIdAndDate.getData();
        this.runnableInter = runnableInter;
    }

    public static FindDateThread getInstance(RunnableInter runnableInter){
        if (findDateThread == null)
            return new FindDateThread(runnableInter);
        else
            return findDateThread;
    }

    public void startThread(){
        threadWorker.start();
    }



    private class ThreadWorker extends Thread{
        private boolean running = true;

        @Override
        public void run() {
            while (running){
                synchronized (list){
                    while (list.isEmpty()){
                        try {
                            list.wait();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                getHouseId();
                }
            }
        }

        private void getHouseId(){
            List<HouseIdAndDate.data> arriveHouse = HouseIdAndDate.getArriveHouse(new Date().getTime());

            HouseIdAndDate.removeData("123");

            //get house ....


            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    System.out.println("send message");
                }
            };

            runnableInter.addRunnable(runnable);

        }
    }
}

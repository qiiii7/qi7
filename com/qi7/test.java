package com.qi7;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class test {

    public static void main(String[] args) {
/*        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                System.out.println("hello world");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask , 0L , 2000L);*/


/*
       List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            list.add(i);
        }

        System.out.println("before---------->" + list);

        insert(list , -2);
//        find(list , 3);
        System.out.println("after----------->" + list);
*/

        ThreadPool threadPoll = ThreadPool.getThreadPoll();
        FindDateThread instance = FindDateThread.getInstance(threadPoll);
        instance.startThread();


    }

    //二分插入
    private static void insert(List<Integer> list , Integer number){
        int low = 0;
        int size = list.size()-1;

        while (low <= size){
            int middle = (low + size) / 2;
            int data = list.get(middle);

            if (middle == list.size() - 1){
                list.add(number);
                return;
            }

            if (middle == 0){
                list.add(0 , number);
                return;
            }

            int before = list.get(middle - 1);
            int after = list.get(middle + 1);




            if (data == number){
                list.add(middle , number);
                return;
            }

            if (number < data && number >= before){
                list.add(middle , number);
                return;
            }

            if (number > data && number <= after){
                list.add(middle+1 , number);
                return;
            }

            if (number > data){
                low = middle + 1;
            }

            if (number < data){
                size = middle - 1;
            }

            System.out.println(middle);

        }
    }

    //二分查找
    private static void find(List<Integer> list , Integer number){
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (number < list.get(middle)) {
                end = middle - 1;
            } else if (number > list.get(middle)) {
                start = middle + 1;
            } else {
                System.out.println(middle);
                return;
            }
        }
    }
}

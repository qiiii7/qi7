package com.qi7;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class HouseIdAndDate {
    private static List<data> data = new ArrayList<>();
    //以毫秒表示 五分钟
    private static final int TIME = 300000;
    static {
        HouseIdAndDate.data data1 = new data("123" , new Date().getTime());
        data.add(data1);
    }

    private HouseIdAndDate(){}

    public static List<HouseIdAndDate.data> getData() {
        return data;
    }

    public static List<HouseIdAndDate.data> addData(data d){
        //二分插入算法
        synchronized (data){
            int low = 0;
            int size = data.size()-1;
            long number = d.getDate();

            while (low <= size){
                int middle = (low + size) / 2;
                long time = data.get(middle).getDate();

                if (middle == data.size() - 1){
                    data.add(d);
                    return data;
                }

                if (middle == 0){
                    data.add(0 , d);
                    return data;
                }

                long before = data.get(middle - 1).getDate();
                long after = data.get(middle + 1).getDate();




                if (time == number){
                    data.add(middle , d);
                    return data;
                }

                if (number < time && number >= before){
                    data.add(middle , d);
                    return data;
                }

                if (number > time && number <= after){
                    data.add(middle+1 , d);
                    return data;
                }

                if (number > time){
                    low = middle + 1;
                }

                if (number < time){
                    size = middle - 1;
                }

            }
        }

        return data;
    }

    public static List<HouseIdAndDate.data> removeData(String houseId){
        synchronized (data){
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getHouse().equals(houseId)) {
                    data.remove(i);
                    return data;
                }
            }
            data.notify();
        }

        return data;
    }

    public static List<data> getArriveHouse(long now){
        List<data> datas = null;

        //如果最前面的一个不满小于五分钟的话直接退出
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDate() - now <= TIME) {

                datas = new ArrayList<>();
                datas.add(data.get(i));

            }else{
                return datas;
            }
        }
        return datas;
    }

    public static void setData(List<HouseIdAndDate.data> data) {
        data = data;
    }

    public static class data{
        private String house;
        private long date;

        public data() {
        }

        public data(String house, long date) {
            this.house = house;
            this.date = date;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "data{" +
                    "house='" + house + '\'' +
                    ", date=" + date +
                    '}';
        }
    }
}

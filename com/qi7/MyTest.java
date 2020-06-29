package com.qi7;

public class MyTest {
    public static void main(String[] args) {

        test2 test2 = new test2();
        test3 test3 = new test3(test2);

        test3.useMethod();


    }
}


interface Inter{
    void use();
    void out();
}

class test2 implements Inter{
    @Override
    public void use() {
        System.out.println("test2 use method");
        say();
    }
    public void say(){
        System.out.println("test2 say method");
    }

    @Override
    public void out() {
        System.out.println("test2 out method");
    }
}

class test3 {
    private Inter inter;
    test3(Inter inter){
        this.inter = inter;
    }
    public void useMethod(){
        inter.use();
    }
}

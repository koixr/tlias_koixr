package com.itheima;

import org.springframework.cglib.core.Local;

public class ThreadLocalTest {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("hello"); //设置主线程

        //创建新线程 线程之间使相互独立的
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()  + ":" + local.get());
            }
        }).start();

        System.out.println(Thread.currentThread().getName()  + ":" + local.get());

        local.remove(); //移除主线程

        System.out.println(Thread.currentThread().getName()  + ":" + local.get());
    }
}

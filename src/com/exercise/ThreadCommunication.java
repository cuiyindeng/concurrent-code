package com.exercise;

import java.util.ArrayList;
import java.util.List;

public class ThreadCommunication {
    static List<Integer> list = new ArrayList<>();
    boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i <= 1000; i++) {
            new Thread(new Producer(i), "生产者线程").start();
            Thread.sleep(1);
            new Thread(new Consumer(), "消费者线程").start();
        }
    }

    static class Producer implements Runnable{
        Integer i;

        public Producer(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (list) {
                list.add(i);
                System.out.println("生产者生产了：" + i);
                list.notify();
            }
        }
    }
    static class Consumer implements Runnable{

        @Override
        public void run() {
            synchronized (list) {
                while (list.size() < 1) {
                    try {
                        System.out.println("消费者等待开始。。。");
                        list.wait();
                        System.out.println("消费者等待结束。。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("消费者消费了：" +  list.remove(0));;
            }
        }
    }
}


package com.zzb.demo;

import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class demo01_Lambda {
    /*Lambda表达式的本质就是一个特殊接口(函数式接口 functional interface)的实例
    1 无参、无返回值*/
    public static void main(String[] args) {
        Runnable r0 = new Runnable() {
            @Override
            public void run() {
                System.out.println("success!");
            }
        };
        r0.run();

        Runnable r1 = () -> System.out.println("success!");
        r1.run();
    }

    @Test
    public void test1() {
       /* 2 有一个参数、无返回值
        Consumer：消费者,接收一个对象，无返回值*/
        Consumer<String> consumer = s -> System.out.println(s.length());
        consumer.accept("nihao");
    }

    @Test
    public void test2() {
        /*3 接收一个参数、有返回值
        参数类型可以省略：类型推断策略
        一个参数的括号可以省略，多个参数不行；return关键字也可以省略
        只有一条语句时大括号也可以省略*/
        Comparator<Integer> comp0 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
        System.out.println(comp0.compare(100, 200));

        Comparator<Integer> comp1 = Integer::compare;
        System.out.println(comp1.compare(-200, 200));
    }
}


package com.zzb.demo;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.*;

public class demo02_FunctionalInterface<T> {
    public static void main(String[] args) {
        double money = 100;
        consumerMoney(money, mon -> {
            mon = mon >= 20 ? mon - 20 : mon;
            System.out.println("您总共消费了20元，兜里还有 " + mon + " 元钱！");
        });
    }

    @Test
    public void test01() {
        List<String> list = Arrays.asList("imoderiii", "xiaohang", "limei");
        filterList(list, str -> str.length() > 6).forEach(System.out::println);
    }

    @Test
    public void test02() {
        System.out.println(getNum(10, num -> num += 100));
    }

    @Test
    public void test03() {
        List<Integer> list = Arrays.asList(100, 200, 300, 400, 500);
        getFilterIntegerList(list, num -> num <= 200 ? num - 10 : (num >= 500 ? num + 10 : num * 2)).forEach(System.out::println);
    }

    @Test
    public void test04() {
        System.out.println(getSum(10, 200, Integer::sum));
    }

    @Test
    public void test05() {
        List<Integer> list1 = Arrays.asList(100, 200, 300, 400, 500);
        List<Integer> list2 = Arrays.asList(10, 20, 30, 40, 50);
        getBinaryIntegerList(list1, list2, (num1, num2) -> num1 < 100 ? num1 + num2 : (num1 > 400 ? num1 - num2 : num1 * num2)).forEach(System.out::println);
    }

    @Test
    public void test06() {
        int num = 10;
        System.out.println(getRes(num, numb -> numb * 2));
    }

    @Test
    public void test07(){
        System.out.println(getSupplier(()->LocalTime.now().getNano()/1000000));
    }

    /*应用 消费模式：接收一个参数，无返回*/
    static void consumerMoney(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    /*应用 断言模式：接收一个参数，返回boolean值*/
    List<String> filterList(List<String> list, Predicate<String> predicate) {
        List<String> filteredList = new ArrayList<>();
        list.forEach(ele -> {
            if (predicate.test(ele)) {
                filteredList.add(ele);
            }
        });
        return filteredList;
    }

    int getNum(int num, Function<Integer, Integer> func) {
        if (num < 100) {
            return num;
        }
        return func.apply(num);
    }

    List<Integer> getFilterIntegerList(List<Integer> list, Function<Integer, Integer> func) {
        List<Integer> resList = new ArrayList<>();
        list.forEach(num -> resList.add(func.apply(num)));
        return resList;
    }

    int getSum(int num1, int num2, BiFunction<Integer, Integer, Integer> func) {
        return func.apply(num1, num2);
    }

    List<Integer> getBinaryIntegerList(List<Integer> list1, List<Integer> list2, BiFunction<Integer, Integer, Integer> func) {
        int minSize = Integer.min(list1.size(), list2.size());
        List<Integer> res = new ArrayList<>(minSize);
        for (int i = 0; i < minSize; i++) {
            res.add(func.apply(list1.get(i), list2.get(i)));
        }
        return res;
    }

    /*接收一个参数，返回类型形同的另一个参数*/
    /*UnaryOperator, BinaryOperator*/
    Integer getRes(int num, UnaryOperator<Integer> operator) {
        return operator.apply(num);
    }

    Integer getSupplier(Supplier<Integer> supplier) {
        return supplier.get();
    }

}



package com.zzb.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;

public class demo03_MethodReferences {
    public static void main(String[] args) {

    }

    @Test
    public void test01() {
        List<Integer> lt1 = Arrays.asList(10, 20, 30, 40);
        List<Integer> lt2 = Arrays.asList(1, 2, 3, 4, 5);
        getBiSum(lt1, lt2, Integer::sum).forEach(System.out::println);
        getBiSum(lt1, lt2, Integer::max).forEach(System.out::println);
    }

    @Test
    public void test02() {
        List<Integer> lt1 = Arrays.asList(10, 20, 30, 40);
        getHexStrFormOfIntList(lt1, Integer::toHexString).forEach(System.out::println);
        getHexStrFormOfIntList(lt1, Integer::toOctalString).forEach(System.out::println);
    }

    @Test
    public void test03() {
        Worker w = getWorker(Worker::new);
        w.setName("imoder");
        w.setAge(19);
        System.out.println(w);
    }

    @Test
    public void test04() {
        System.out.println(getWorkerWithNameAndAge("imoder", 18, Worker::new));
    }

    @Test
    public void test05() {
        Function<Integer, ArrayList<Integer>> func = ArrayList::new;
        ArrayList<Integer> lt = func.apply(4);
        lt.add(1);
        System.out.println(lt);
    }

    List<Integer> getBiSum(List<Integer> lt1, List<Integer> lt2, ToIntBiFunction<Integer, Integer> function) {
        List<Integer> resList = new ArrayList<>();
        int minSize = Integer.min(lt1.size(), lt2.size());
        for (int i = 0; i < minSize; i++) {
            resList.add(function.applyAsInt(lt1.get(i), lt2.get(i)));
        }
        return resList;
    }

    List<String> getHexStrFormOfIntList(List<Integer> lt, Function<Integer, String> function) {
        List<String> resList = new ArrayList<>();
        lt.forEach(ele -> resList.add(function.apply(ele)));
        return resList;
    }

    Worker getWorker(Supplier<Worker> supplier) {
        return supplier.get();
    }

    Worker getWorkerWithNameAndAge(String name, int age, BiFunction<String, Integer, Worker> func) {
        return func.apply(name, age);
    }


}

class Worker {
    private String name;
    private int age;

    public Worker() {
    }

    public Worker(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Worker get() {
        return new Worker("imoder", 18);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

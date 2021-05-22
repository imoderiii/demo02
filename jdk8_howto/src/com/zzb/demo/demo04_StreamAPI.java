package com.zzb.demo;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class demo04_StreamAPI {

    public static void main(String[] args) {

    }

    @Test
    public void test01() {
        List<Integer> lt1 = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = lt1.stream();
        Stream<Integer> parallelStream = lt1.parallelStream();
        System.out.println(stream);
        System.out.println(parallelStream);
    }

    @Test
    public void test02() {
        String[] strs = new String[]{"imoder", "xiaogang", "xiaohong"};
        int[] ints = new int[]{1, 2, 3, 4, 5, 6};
        Stream<String> stream = Arrays.stream(strs);
        IntStream intStream = Arrays.stream(ints);
        System.out.println(intStream);
        System.out.println(stream);
    }

    @Test
    public void test03() {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        Stream<int[]> stream = Stream.of(ints);
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
        System.out.println(integerStream);
        System.out.println(stream);
    }

    @Test
    public void test04() {
        Stream<Integer> iterateSr = Stream.iterate(0, num -> num + 2);
        iterateSr.limit(10).forEach(System.out::println);
    }

    @Test
    public void test05() {
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    @Test
    public void test06() {
        int[] ints = {100, 23, 23, 12, 14, 56, 32, 10, 928, 23, 455};
        IntStream intStream = Arrays.stream(ints);
        intStream.distinct().filter(num -> num < 100).limit(5).skip(2).forEach(System.out::println);
    }

    private List<Man> list = new ArrayList<>();

    {
        list.add(new Man("齐天大圣", 500, true, 500.0));
        list.add(new Man("唐僧", 100, true, 1100.0));
        list.add(new Man("猪八戒", 300, false, 400.0));
        list.add(new Man("沙僧", 300, false, 300.0));
        list.add(new Man("白龙马", 80, true, 100.0));
        list.add(new Man("白龙马", 80, true, 100.0));
    }

    @Test
    public void test07() {
        // list.forEach(System.out::println);
        list.stream().filter(man -> man.getAge() > 100 && man.getSalary() < 500).forEach(System.out::println);
    }

    @Test
    public void test08() {
        list.stream().map(man -> {
            if (man.getAge() > 300) {
                man.setSalary(man.getSalary() + 100);
            } else {
                man.setSalary(man.getSalary() + 50);
            }
            return man.getSalary();
        }).forEach(System.out::println);

        list.stream().peek(man -> {
            if (man.getSalary() > 300) {
                man.setSalary(man.getSalary() + 100);
            } else {
                man.setSalary(man.getSalary() + 50);
            }
        }).forEach(System.out::println);
    }

    @Test
    public void test09() {
        //获取Man姓名不超过3的人的姓名和薪资
        list.stream().map(Man::getName).filter(name -> name.length() <= 3).forEach(System.out::println);
    }

    @Test
    public void test10() {
        String[] str = {"imoder", "nihao!"};
        // Arrays.stream(str).map(demo04_StreamAPI::fromString2Stream).forEach(strSr -> {
        //     strSr.forEach(System.out::println);
        // });

        Arrays.stream(str).flatMap(demo04_StreamAPI::fromString2Stream).forEach(System.out::println);

    }

    static Stream<Character> fromString2Stream(String str) {
        List<Character> resList = new ArrayList<>();
        for (char c : str.toCharArray()) {
            resList.add(c);
        }
        return resList.stream();
    }

    @Test
    public void test11() {
        String[] str = {"imoder", "nihao!"};
        Arrays.stream(str).flatMap(demo04_StreamAPI::fromString2Stream).sorted().forEach(System.out::println);
    }

    @Test
    public void test12() {
        list.stream().sorted(Comparator.comparingInt(Man::getAge)).forEach(System.out::println);
    }

    @Test
    public void test13() {
        list.stream().sorted(Comparator.comparingInt(Man::getAge).thenComparingDouble(Man::getSalary)).forEach(System.out::println);
    }

    @Test
    public void test14() {
        System.out.println(list.stream().peek(man -> man.setSalary(man.getSalary() + 20)).allMatch(man -> man.getSalary() < 1000));
        System.out.println(list.stream().peek(man -> man.setSalary(man.getSalary() + 20)).anyMatch(man -> man.getSalary() < 1000));
        System.out.println(list.stream().peek(man -> man.setSalary(man.getSalary() + 20)).noneMatch(man -> man.getSalary() < 1000));
    }

    @Test
    public void test15() {
        System.out.println(list.stream().filter(man -> man.getSalary() < 1000).count());
        Optional<Man> man = list.stream().distinct().limit(0).min(Comparator.comparingDouble(Man::getSalary));
        System.out.println(man.orElse(new Man("imoder", 18, true, 100.00)));
    }

    @Test
    public void test16() {
        System.out.println(list.stream().min(Comparator.comparingInt(Man::getAge)).filter(man -> man.getSalary() > 1000));
    }

    @Test
    public void test17() {
        Integer reduce = Stream.iterate(0, n -> n + 1).limit(10).reduce(0, Integer::sum);
        System.out.println(Stream.iterate(0, n -> n + 1).limit(10).reduce(0, Integer::sum));
        System.out.println(list.stream().map(Man::getSalary).reduce(Double::sum));
    }

    @Test
    public void test18() {
        List<String> list = this.list.stream().distinct().map(Man::getName).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test19() {
        System.out.println(list.stream().map(Man::getAge).collect(Collectors.toSet()));
    }

    @Test
    public void test20() {
        System.out.println(list.stream().mapToInt(Man::getAge).sum());
        System.out.println((Double) list.stream().limit(3).mapToDouble(Man::getSalary).sum());
        System.out.println(list.stream().collect(Collectors.averagingInt(Man::getAge)));
    }

    @Test
    public void test21() {
        System.out.println(list.stream().map(Man::getName).collect(Collectors.joining("-", "[", "]")));
    }

}

class Man {
    private String name;
    private int age;
    private boolean gender;
    private double salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Man man = (Man) o;
        return age == man.age && gender == man.gender && Double.compare(man.salary, salary) == 0 && Objects.equals(name, man.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender, salary);
    }

    @Override
    public String toString() {
        return "Man[" + name + " " + age + " " + gender + " " + salary + "]";
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Man() {
    }

    public Man(String name, int age, boolean gender, double salary) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }
}
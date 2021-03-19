package com.zzb.deno;

public class demo02 {
    public static void main(String[] args) {
        A a = new A("imoder", 18);
        System.out.println(a);
        a.test();
        System.out.println(a.name());
        System.out.println(a.age());
        A.staticTest();
    }

}

record A(String name, int age) {

    public static int recored = 100;

    public void test() {
        System.out.println("test!");
    }

    public static void staticTest() {
        System.out.println("staticTest");
    }

    public A {
        System.out.println("重写有参构造方法！");
        age = Math.min(age, 18);
        System.out.println("age:" + age);

    }
}

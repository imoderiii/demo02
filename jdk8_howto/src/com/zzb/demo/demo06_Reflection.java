package com.zzb.demo;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("ALL")
public class demo06_Reflection {
    public static void main(String[] args) {
        System.out.println(Demo.class.getName());
        System.out.println(new Demo("imoder", 1).getClass().getName());
    }

    @Test
    public void test01() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.zzb.demo.Demo");
        System.out.println(aClass.getClassLoader());
        System.out.println(aClass.getName());
    }

    @Test
    public void test02() throws Exception {
        Class<?> clazz = Class.forName("com.zzb.demo.Demo");
        for (Constructor<?> constr : clazz.getConstructors()) {
            System.out.println(constr);
        }
        System.out.println();
        for (Constructor<?> constr : clazz.getDeclaredConstructors()) {
            System.out.println(constr);
        }
    }

    @Test
    public void test03() throws Exception {
        Class<?> clazz = Class.forName("com.zzb.demo.Demo");
        Demo imoder = (Demo) clazz.getConstructor(String.class, int.class).newInstance("imoder", 18);
        System.out.println(imoder);

        Constructor<?> constr = clazz.getDeclaredConstructor(float.class);
        constr.setAccessible(true);
        System.out.println(constr.newInstance(18));

        System.out.println(clazz.getDeclaredConstructor(int.class).newInstance(19));

    }

    @Test
    public void test04() throws Exception {
        Demo demo = new Demo();
        Class<Demo> clazz = Demo.class;
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(demo));
        name.set(demo, "imoder");
        System.out.println(demo);
    }

    @Test
    public void test05() throws Exception {
        Demo demo = new Demo("imoder", 19);
        Class<Demo> clazz = Demo.class;
        Method m1 = clazz.getMethod("method1");
        m1.invoke(demo);

        Method m2 = clazz.getMethod("method2", String.class);
        m2.invoke(demo, "imoder");

        Method m22 = clazz.getMethod("method2", String.class, int.class);
        m22.invoke(demo, "imdoer", 19);

        Method m3 = clazz.getDeclaredMethod("method3");
        m3.setAccessible(true);
        System.out.println(m3.invoke(demo));

        Method nameM = clazz.getMethod("setName", String.class);
        System.out.println(nameM.getReturnType());
        System.out.println(nameM.getAnnotation(Deprecated.class));
        System.out.println(nameM.getDeclaringClass());
        System.out.println(nameM.getGenericExceptionTypes());
    }

    @Test
    public void test06() throws Exception {
        Method m = demo06_Reflection.class.getDeclaredMethod("main", String[].class);
        m.invoke(null, (Object) new String[]{""});
    }

    @Test
    public void test07() {
        System.out.println(new Object[]{new String[]{"1", "2", "3"}});
    }

    @Test
    public void test08() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("src/demo06.properties")));
        String className = prop.getProperty("class");
        String methodName = prop.getProperty("method");
        Class<?> clazz = Class.forName(className);
        clazz.getDeclaredMethod(methodName).invoke(clazz.getConstructor().newInstance());
    }

    @Test
    public void test09() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(list);
        Class<? extends List> clazz = list.getClass();
        clazz.getMethod("add", Object.class).invoke(list, 2);
        System.out.println(list);
        System.out.println(list instanceof ArrayList);
    }

    private Class<Demo> clazz = Demo.class;

    @Test
    public void test10() {
        //简单名、包名、名称、规范名、类型名
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getPackage());
        System.out.println(clazz.getName());
        System.out.println(clazz.getCanonicalName());
        System.out.println(clazz.getTypeName());
    }

    @Test
    public void test11() {
        // 获取父类、、、获得包裹着它的类。
        System.out.println(clazz.getSuperclass().getName());
        // 获取所有公有内部类
        for (Class<?> aClass : clazz.getClasses()) {
            System.out.println(aClass.getName());
        }
        System.out.println();
        // 获取所有显式声明的内部类
        for (Class<?> aClass : clazz.getDeclaredClasses()) {
            System.out.println(aClass);
        }
        Class<Demo.Inner> cz = Demo.Inner.class;
        //获得声明它的类(匿名内部类返回null)
        System.out.println(cz.getDeclaringClass().getName());
        System.out.println(cz.getEnclosingMethod());
    }

    @Test
    public void test12() {
        // 检验类的访问控制符、泛型类型
        Class<Demo02> cz = Demo02.class;
        int modifiers = cz.getModifiers();
        System.out.println(Modifier.isFinal(modifiers));
        System.out.println(Modifier.toString(modifiers));

        System.out.println(cz.getTypeName());
        for (TypeVariable<Class<Demo02>> tp : cz.getTypeParameters()) {
            System.out.println(tp.getName());
            System.out.println(tp);
        }
    }

    @Test
    public void test13() {
        // 获取实现的接口信息
        Class<Demo02> cz = Demo02.class;
        for (Class<?> i : cz.getInterfaces()) {
            System.out.println(i.getName());
        }

        System.out.println();
        for (AnnotatedType at : cz.getAnnotatedInterfaces()) {
            System.out.println(at.getType().getTypeName());
        }

        System.out.println();
        for (Type gi : cz.getGenericInterfaces()) {
            System.out.println(gi);
        }
    }

    @Test
    public void test14() {
        int[] ints = (int[]) Array.newInstance(int.class, 3);
        ints[0] = 1;
        for (int i : ints) {
            System.out.println(i);
        }
        Array.set(ints, 0, 100);
        Array.set(ints, 1, 200);
        Array.set(ints, 2, 300);
        System.out.println("ints[0]" + Array.get(ints, 0));
        System.out.println("ints[1]" + Array.get(ints, 1));
        System.out.println("ints[2]" + Array.get(ints, 2));

    }

    @Test
    public void test15() throws Exception {
        Class<?> ints = Class.forName("[I");
        Class<?> strs = Class.forName("[Ljava.lang.String;");
        System.out.println(ints.getName() + " " + strs.getName());

    }

    @Test
    public void test16() {
        String[] strs = new String[]{"a", "b", "c"};
        System.out.println(strs.getClass().getComponentType().getName());
    }

    @Test
    public void test17() {
        //注解的应用
        Annotation[] annos = Demo05.class.getAnnotations();
        for (Annotation anno : annos) {
            System.out.println(anno);
            if (anno instanceof MyAnnotation) {
                System.out.println(((MyAnnotation) anno).name());
                System.out.println(((MyAnnotation) anno).value());
            }
        }

        System.out.println();
        MyAnnotation anno = Demo05.class.getAnnotation(MyAnnotation.class);
        System.out.println(anno.name() + " " + anno.value());
    }

    @Test
    public void test18() throws Exception {
        // 泛型返回值类型检查
        Class<Demo05> cla = Demo05.class;
        Method method = cla.getMethod("getList");
        System.out.println(method.getReturnType().getName());
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            ParameterizedType paraedType = (ParameterizedType) type;
            System.out.println(paraedType.getOwnerType());
            System.out.println(paraedType.getRawType());
            System.out.println(paraedType.getActualTypeArguments()[0].getTypeName());
            System.out.println((Class) paraedType.getActualTypeArguments()[0]);
        }

        System.out.println("----------------");
        Method getMap = cla.getMethod("getMap");
        ParameterizedType paraType = (ParameterizedType) getMap.getGenericReturnType();
        for (Type i : paraType.getActualTypeArguments()) {
            System.out.println(i.getTypeName());
        }

        //泛型方法参数类型检查
        System.out.println("----------------");
        Method method1 = cla.getMethod("setNativeList", List.class);
        for (Type t : method1.getGenericParameterTypes()) {
            if (t instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) t;
                for (Type i : paramType.getActualTypeArguments()) {
                    System.out.println(i.getTypeName());
                }
            }
        }

        //泛型字段检查
        System.out.println("----------------");
        Field nativeMap = cla.getDeclaredField("nativeMap");
        nativeMap.setAccessible(true);
        for (Type i : ((ParameterizedType) nativeMap.getGenericType()).getActualTypeArguments()) {
            System.out.println(i.getTypeName());
        }

    }

}

@MyAnnotation(value = 1)
class Demo05 {

    private List<Integer> nativeList = Arrays.asList(1, 2, 3);
    private Map<String, Integer> nativeMap;

    {
        nativeMap.put("A", 100);
        nativeMap.put("B", 200);
        nativeMap.put("C", 300);
    }

    public List<Integer> getList() {
        return nativeList;
    }

    public Map<String, Integer> getMap() {
        return nativeMap;
    }

    public void setNativeList(List<Integer> list) {
        this.nativeList = list;
    }

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String name() default "imoder";

    int value() default 100;
}

class Demo04 extends Demo03<Demo> {
    @Override
    public Demo getT(Demo s) {
        return s;
    }
}

abstract class Demo03<T> {
    public static void main(String[] args) {
        Demo04 d = new Demo04();
    }

    {
        // String typeName = this.getClass().getGenericSuperclass().getTypeName();
        // System.out.println(typeName.split("[<>]")[1]);
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        System.out.println(type.getActualTypeArguments()[0]);
    }

    abstract T getT(T t);
}

abstract class Demo02<T, U> implements Comparable<U>, Serializable {

}

class Demo extends demo02_FunctionalInterface {
    private String name;
    private int indeftity;

    public class Inner {
        public Inner() {
            System.out.println("class inner");
        }
    }

    class InnerDefault {
        public InnerDefault() {
            System.out.println("innder default class");
        }
    }

    public static class InnerStatic {
        public InnerStatic() {
            System.out.println("inner static class");
        }
    }

    public int getIndeftity() {
        return indeftity;
    }

    public void setIndeftity(int indeftity) {
        this.indeftity = indeftity;
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(String name) throws Exception {
        this.name = name;
    }

    /*public void method1() {
        System.out.println("public no param. mehtod!");
    }

    public void method2(String name) {
        System.out.println("public one param. mehtod!" + name);
    }

    public void method2(String name, int age) {
        System.out.println("public nult. param. reload mehtod!" + name + age);
    }

    private String method3() {
        return "imoder";
    }

    public void invokeMethod01() {
        System.out.println("invokeMethod01");
    }

    public void invokeMethod02() {
        System.out.println("invokeMethod02");
    }

    Demo(int b) {
        System.out.println("默认构造器");
    }

    public Demo() {
        System.out.println("无参构造器");
    }

    public Demo(String name) {
        System.out.println("单参构造器");
        this.name = name;
    }

    protected Demo(boolean b) {
        System.out.println("受保护的构造器");
    }

    private Demo(float b) {
        System.out.println("私有的构造器");
    }*/

    public Demo() {
    }

    public Demo(String name, int indeftity) {
        // System.out.println("多参构造器");
        this.name = name;
        this.indeftity = indeftity;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", indeftity=" + indeftity +
                '}';
    }
}


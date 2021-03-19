package com.zzb.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class demo07_DynamicProxy {
    public static void main(String[] args) {
        IWorker stu = new Student();
        InvocationHandler handler = new MyInvocationHandler(stu);
        Object o = Proxy.newProxyInstance(stu.getClass().getClassLoader(), stu.getClass().getInterfaces(), handler);
        System.out.println();
        System.out.println(o);
        IWorker stuProxy = (IWorker) o;
        System.out.println(stuProxy.readBook(1));
        stuProxy.study();
    }

    @Test
    public void test01() {
        IWorker stu = new Student();
        IWorker stuProxy = (IWorker) Proxy.newProxyInstance(Student.class.getClassLoader(), Student.class.getInterfaces(), (o, method, args) -> {
            IWorker target = new Student();
            System.out.println("=》动态代理开始");
            Object res;
            if ("readBook".equals(method.getName())) {
                System.out.println("-》拦截readbook成功");
                res = method.invoke(target, args);
                System.out.println("-》拦截readbook结束");
                System.out.println("=》动态代理结束");
                return res;
            } else {
                return method.invoke(target, args);
            }
        });

        System.out.println(stuProxy.readBook(1));
    }

    @Test
    public void test02() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);

        People stuProxy = (People) Enhancer.create(People.class, People.class.getInterfaces(), (MethodInterceptor) (o, method, objects, methodProxy) -> {
            Object res;
            System.out.println("CGLIB代理开始");
            System.out.println("执行方法：" + methodProxy.getSignature());
            System.out.println("方法参数：" + Arrays.toString(objects));
            res = methodProxy.invokeSuper(o, objects);
            System.out.println("CGLIB代理结束");
            return res;
        });

        System.out.println(stuProxy.readBook(100));
    }
}

interface IWorker {
    void study();

    int readBook(int n);
}

class People {

    public void study() {
        System.out.println("I'm studing now……");
    }

    public int readBook(int n) {
        System.out.println("I'm reading" + n + "book(s)……");
        return n;
    }

}

class Student implements IWorker {

    @Override
    public void study() {
        System.out.println("I'm studing now……");
    }

    @Override
    public int readBook(int n) {
        System.out.println("I'm reading" + n + "book(s)……");
        return n;
    }
}

class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler() {
        super();
    }

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("readBook".equals(method.getName())) {
            System.out.println("proxy start……");
            System.out.println(proxy);
            System.out.println(method.getReturnType().getName());
            System.out.println(Arrays.toString(args));
            Object res = method.invoke(target, args);
            System.out.println(res);
            System.out.println("proxy end……");
            return res;
        } else {
            return method.invoke(target, args);
        }
    }
}
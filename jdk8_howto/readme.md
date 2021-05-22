# demo01_Lambda
函数式接口的对象实例
1 ()->{}
2 param->{}
3 (param1,param2)->{}
4 param->{
    //some code!  
  }

# demo02_FunctionalInterface
1 void consumer<T>
  void BiConsumer<T,U>
2 R Function<T>
  R BiFunction<T,U>
  int ToIntFunction<T>
  int ToIntBiFunction<T,U>
  double ToDoubleFunction<T>
  double ToDoubleFunction<T,U>
  long ToLongFunction<T>
  long ToLongFunction<T,U>
  R IntFunction<Interger>
  R DoubleFunction<Double>
  R LongFunction<Long>
3 T UnaryOperator<T>
  T BinaryOperator<T1,T2>
4 Boolean Predicate<T>
  Boolean BiPredicate<T,U>
5 T Supplier<>

# demo03_MethodReferences
Lambda表达式的深层次表达，本质上也是函数接口的对象实例
1 对象::实例方法
2 类::静态方法/非静态方法
3 构造器引用 类::new
4 数组引用 String[]::new

# demo04_StreamAPI
java.util.stream;
函数式编程;
Stream是处理集合的关键抽象;StreamAPI可以对集合数据进行快速查找、过滤和映射
Stream和Collection的区别：
1 Collection与某一段内存数据有关，是一种静态的数据结构；而Stream与计算有关(CPU)
2 Stream只会考虑计算，自身不会储存数据；
3 Stream不会改变源对象，每次执行返回一个包含结果数据信息新Stream对象；
4 Stream操作具有延迟执行的特性。
步骤：获取数据源的Stream对象->中间链式操作(在终止操作执行时开始执行：惰性求值)->终止操作。
获取Stream对象方式：
1：通过集合
   获取流：Stream<Integer> stream = lt1.stream();
   获取并行流：Stream<Integer> parallelStream = lt1.parallelStream();
2: 通过数组
    获取流：Stream<String> stream = Arrays.stream(strs);
           IntStream intStream = Arrays.stream(ints);
3: Stream.of(T ... valus)
    获取流：Stream<int[]> stream = Stream.of(ints);
           Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
4：创建无限流：Stream.iterate()迭代式和Stream.generate()生成式
    迭代流：Stream<Integer> iterateSr = Stream.iterate(0, num -> num + 2);
           iterateSr.limit(10).forEach(System.out::println);
    生成流：Stream.generate(Math::random).limit(10).forEach(System.out::println);

中间操作：
1 筛选与切片：filter(Predicate p); distinct(); limit(long n); skip(long n);
2 映射：map(Function f); peek(Consumer c); flatMap(Function f)
3 排序：sorted(Comparator com) 比较器链
       list.stream().sorted(Comparator.comparingInt(Man::getAge).thenComparingDouble(Man::getSalary)).forEach(System.out::println);
终止操作：
1 终止操作会从流水线上获取结果，其类型可以是除Stream外的任意类型(包括void);流在终止操作之后不可继续使用；
2 匹配：allMatch(Predicate p); anyMatch(Predicate p); noneMatch(Predicate p);
3 查找：findFirst(); findAny(); count(); max(Comparator com); min(Comparator com); sum()
4 内部迭代：forEach(Consumer c)
5 规约：reduce(T identity, BinaryOperator(T,U))
6 收集：collect(Collector c)
    Collectors.toList(); Collectors.toSet(); Collectors.toCollection(ArrayList::new);
    Collectors.counting();  Collectors.summingInt(ToIntFunction f); averagingInt(ToIntFunction f);
    Collectors.joining("-", "[", "]");

# demo05_OptionalClass
java.util.Optional;
这是一个可以为null的容器对象，如果值存在则isPresent()返回true,且可以由get()返回该对象；对象为null时表示未Optional.null;
1: Optional.of(T t); Optional.empty(); Optional.ofNullable() -> Optional class;
2: bool isPresent(); void ifPresent(Consumer con);
3: T get() -> Exception if null; T orElse(T t); T orElseGet(Supplier other); T orElseThrow(Supplier exceptionSup)

# demo06_Reflection
Java中创建的所有类本身均是java.lang.Class类的实例对象(Class对象); 基本的Java类型和关键字void也表示为Class对象;
Class对象是在加载类时由JVM以及通过调用类加载器中的defineClass()方法自动构造的;
0 获取类的全限定名：Foo.class.getName(); foo.getClass().getName(); getCanonicalName(); getSimplelName()
1 获取类对象有3种方式(获取的是同一ClassLoader下的同一个类对象):
    1: Class.forName()
    2: Foo.class
    3: new Foo().getClass()
2 创建类的实例对象——通过类对象来获取构造器
    Constructor[] getConstructors()：所有"公有的"构造方法
    Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)
    Constructor getConstructor(Class... parameterTypes): 获取单个的"公有的"构造方法：
    Constructor getDeclaredConstructor(Class... parameterTypes): 获取"某个构造方法"可以是私有的，或受保护、默认、公有
    constr.newInstance(...args)->Object obj
    constr.setAccessible(true);暴力访问private.
3 获取和设置成员变量
    Field getField(): 获取public的, 包括从父类继承来的字段;
    Field getDeclaredField(): 可以获取本类所有的字段,包括private的,但是不能获取继承来的字段
    field.set(obj,fieldValue);
    filed.setAccessible(true);暴力访问private.
4 获取和使用成员方法
    Method getMethod(String name ，Class<?>… parameterTypes):获取"公有方法"；（包含了父类的方法也包含Object类）
    Method getDeclaredMethods(String name ，Class<?>… parameterTypes) :获取成员方法，包括私有的(不包括继承的)
    method.invoke(obj, ...params);
    METHOD.setAccessible(true);暴力访问private.
    获取方法其他属性：   
    getReturnType());
    getAnnotation(Deprecated.class)
    getDeclaringClass());
    getGenericExceptionTypes());
    获取main()方法
5 应用：
    1: 从文件加载类名和方法名，使用反射方式来执行指定方法从而尽可能消除程序耦合;
    2: 利用反射机制越过泛型检查
6 java.lang.reflect.Array

# demo07_DynamicProxy
1 JDK实现：被代理类需要实现接口且接口中需要声明被拦截方法
    Class Proxy{
        static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h){}
    }
    Interface InvocationHandler{
        Object invoke(Object o, Method method, Object[] args)throws Throwable;
    }
2 CGLib实现:
    Cglib是一个优秀的动态代理框架，底层使用ASM在内存中动态的生成被代理类的子类，使用CGLIB无需代理类实现任何接口;
    Cglib具有简单易用且运行速度要远远快于JDK的Proxy动态代理。
    Cglib有两种可选方式: 继承和引用; 第一种方式可以直接通过super调用target方法，但spring不支持这种方式，因为这样这个target对象就不能被spring所管理。
    CGLib的核心类：
        net.sf.cglib.proxy.Enhancer: 主要的增强类;
        net.sf.cglib.proxy.MethodInterceptor: 主要的方法拦截类，它是Callback接口的子接口，需要用户实现;
        net.sf.cglib.proxy.MethodProxy: JDK的java.lang.reflect.Method类的代理类，可以方便的实现对源对象方法的调用且速度更快；
        如：Object o = methodProxy.invokeSuper(proxy, args);//虽然第一个参数是被代理对象，也不会出现死循环的问题。
    bug:
        手动引入jar包可能会报ASM缺失错误，需要引入该Jar包；Maven依赖不会有此问题！
        注意版本问题，本例中使用asm-3.3.1.jar和cglib-2.2.jar不会报错！

3 区别：Java动态代理使用Java原生的反射API进行操作，在生成类上比较高效；CGLIB使用ASM框架直接对字节码进行操作，在类的执行过程中比较高效。





























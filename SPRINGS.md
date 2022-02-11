---
typora-copy-images-to: upload
---

# Spring

> `Spring`使用的是基本的[JavaBean](https://baike.baidu.com/item/JavaBean/529577)来完成以前只可能由[EJB](https://baike.baidu.com/item/EJB/144195)完成的事情。然而，Spring的用途不仅仅限于服务器端的开发。从简单性、可测试性和松耦合性角度而言，绝大部分Java应用都可以从Spring中受益。
>
> Spring是一个轻量级的**控制反转**和**面向切面**的容器的框架。

参考：https://www.bilibili.com/video/BV1wi4y1P7Jm?p=5&spm_id_from=333.1007.top_right_bar_window_history.content.click

## Spring框架

![img](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.bubuko.com%2Finfo%2F201912%2F20191215203642824082.png&refer=http%3A%2F%2Fimage.bubuko.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644468709&t=debf909e169b692277d269980268a587)

### Test

> 面向全过程的测试功能。

### Core Container

> 核心容器功能，

#### Beans

> 业务对象

#### Context

> 业务上下文

### 中间层

#### IoC

> 控制反转，`Inverse of Control`，指的是将数据的创建与管理交给Spring容器。

#### AOP

> 面向切面编程，指的是在无需更改业务代码的情况下实现对业务的增强。

### Data

> Spring提供的数据管理服务



### Web



## Spring Framework

### SpringIoC

> 指的是将数据的创建与管理交给Spring容器，接下来的项目将会演示Spring是如何管理对象的。
>
> - 如何将对象交给Spring容器
> - 如何从Spring容器获取对象
> - 创建对象的几种方式
>     - 基于XML，详情查看 [XML版]( # XML版)。
>     - 基于注解，详情查看 [注解版]( # 注解版)。
> - Spring容器如何完成对对象属性赋值

#### 部署

##### 创建工程

| 创建maven工程，如下                                          |
| ------------------------------------------------------------ |
| ![image-20220121172054662](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220121172056.png) |

##### 添加依赖

> Spring IoC的核心组件为`Beans`、`core`、`context`、`aop`、`expression`。但是在context中已经包含了这些组件。
>
> ![image-20220111125629160](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220111125725.png)

```xml
<!-- 在pom文件中添加如下依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.5.RELEASE</version>
</dependency>
```

##### 创建配置文件

> 在Spring中，我们默认的将配置文件命名为`applicationContext.xml`，且位于resource下面。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 标准模板 -->
</beans>
```

#### 使用

> 在配置文件中进行配置实体类，将实体的创建交由容器管理，这里也是`IoC`的体现
>
> - **IoC**（`Inverse of Control`）:控制反转，将实体的创建交由Spring容器管理，通过Spring对象工厂完成对象的创建，而不是用户去创建。
>
>     ```xml
>     <!-- 通过bean将实体类配置给Spring进行管理,id表示实体类的唯一表示-->
>     <bean id="student1" class="com.marshio.aop.pojo.Student"/>
>     ```
>
> - **DI**（`Dependency Injection`）：依赖注入，在Spring容器完成对象创建的同时，依赖Spring容器完成对象属性的赋值。
>
>     ```xml
>     <!-- 利用依赖注入给对象参数赋值   -->
>     <bean id="student2" class="com.marshio.aop.pojo.Student">
>         <!--简单类型 -->
>         <property name="stuNum" value="2017" />
>         <property name="stuName" value="mas" />
>     </bean>
>     ```

##### 实体类

```java
//创建实体类
public class Student {
    private int id;
    private String stuNum;
    private String stuName;
    private String stuSex;
    private int stuAge;
    private Date stuBirth;
    private Clazz clazz;
    private List<String> hobbies;
}
```

```java
public class User {
    private int uid;
    private String userNum;
    private String userName;
}
```

> 声明实体类之后，需要配置实体类将实体类交由Spring管理。
>
> 配置实体类有两种方式
>
> - XML版
> - 注解版

##### XML版

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 通过bean将实体类配置给Spring进行管理,id表示实体类的唯一表示-->
    <bean id="student1" class="com.marshio.aop.pojo.Student"/>
    
    <!-- 利用依赖注入给对象参数赋值   -->
    <bean id="student2" class="com.marshio.aop.pojo.Student">
        <!--简单类型 -->
        <property name="stuNum" value="2017" />
        <property name="stuName" value="mas" />
        <property name="stuAge" value="20" />
        <property name="stuSex" value="0" />
        <!--集合类型 -->
        <property name="hobbies">
            <list>
                <value>旅游</value>
                <value>电影</value>
                <value>Java</value>
            </list>
        </property>
    </bean>
</beans>
```



##### 注解版

> 注解即利用注解将对象交给Spring容器管理，在Spring中共有四种将对象交给Spring容器管理的注解。
>
> - Service：主要声明业务处理类，`Service`层。
> - Controller：主要声明控制器类，`servlet`、`controller`层
> - Repository：主要声明持久化配置类，`Dao`层
> - Component：除`controller`、`servlet`、`service`、`Dao`层之外的类使用此注解。
>
> 其他常用对象声明注解
>
> - Scope：类注解，相当于`bean`标签的`scope`属性，`@Scope("prototype")` 表示声明当前类为非单例模式
> - Lazy：类注解，声明一个单例类是否为懒汉模式，`@Lazy(true)` 表示声明为懒汉模式，默认为饿汉模式
> - PostConstruct：方法注解，声明该方法为类的初始化方法，在构造器之后执行，相当于`bean`的`init-method`属性
> - PreDestory：方法注解，声明该方法为类的销毁方法，对象从容器中释放之前，相当于`bean`的`destory-method`方法
> - Autowired：属性注解，方法注解，声明当前属性自动装配，默认为`byType`。
> - Resource：属性注解，

**更新配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 声明使用注解配置 -->
    <context:annotation-config/>

    <!-- 声明Spring工厂注解的扫描范围 -->
    <context:component-scan base-package="com.marshio.ioc.pojo"/>
</beans>
```



- **Service**

```java
```

-  **Controller**

```java
```

-  **Repository**

```java

```

-  **Component**

```java
//component相当于bean，value相当于id，可省略，默认为当前类名（首字母小写）
@Component(value = "user")
public class User {
    private int uid;
    private String userNum;
    private String userName;
}
```



#### 获取对象

```java
@Test
public void springIoCTest() {
    //获取配置文件，并对配置文件进行解析，通过解析可以获取对象的类名，参数，构造方法，初始值等
    ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    //通过解析获取XML配置的对象
    Student student1 = (Student) applicationContext.getBean("student1");
    System.out.println(student1.toString());

    //获取依赖注入含参数对象
    Student student2 = (Student) applicationContext.getBean("student2");
    System.out.println(student2.toString());
}
```

#### 源码解析

##### 加载配置文件

```java
//初始化Spring容器，即找到配置文件，并加载配置文件,在这里我们传入了配置文件的路径
ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
```

##### 解析配置文件

如上代码`ClassPathXmlApplicationContext`构造如下

```java
public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
    // configLocation即我们传入的配置文件名称，这里又调用了另外一个构造函数，如下
    this(new String[]{configLocation}, true, (ApplicationContext)null);
}
//构造函数如下，三个参数，第一个是配置文件地址，第二个是是否刷新，第三个是配置文件的父级配置文件
public ClassPathXmlApplicationContext(
    String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
    throws BeansException {
	//这里parent为空
    super(parent);
    //这里调用了设置配置文件的路径方法，代码如下
    setConfigLocations(configLocations);
    if (refresh) {
        refresh();
    }
}

//设置配置文件，参数为配置文件地址
public void setConfigLocations(@Nullable String... locations) {
    if (locations != null) {
        Assert.noNullElements(locations, "Config locations must not be null");
        this.configLocations = new String[locations.length];
        //循环配置文件
        for (int i = 0; i < locations.length; i++) {
            // 解析配置文件，代码如下
            this.configLocations[i] = resolvePath(locations[i]).trim();
        }
    }
    else {
        this.configLocations = null;
    }
}

//解析配置文件
protected String resolvePath(String path) {
    //这里面是如何具体解析的，可以使用debug一步一步跟着解析
    return getEnvironment().resolveRequiredPlaceholders(path);
}
```

### 代理模式

> 又称中介模式，说代理可能不太懂，但是如果说中介模式的话，你应该就懂了，毕竟生活中的中介可是非常多的。
>
> 代理模式即在**不修改**被代理对象的基础上，通过扩展代理类，进行一些功能的附加与增强。
>
> 需要注意的是，**被代理对象与代理对象都需要实现或继承同一接口或类**。
>
> 代理模式分为**静态代理**和**动态代理**。

![image-20220124160407284](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220124160408.png)

> 那么为什么需要中介模式呢？
>
> - 职责清晰
> - 增强了可扩展性
> - 智能化，动态代理
>
> 当然，代理模式同样会带来一些问题。
>
> - 请求多了一层，降低处理速度，反应变慢
> - 复杂的逻辑

#### 静态代理

> 静态即不变，代理单一固定。类似婚介所、房产中介，他们都是固定的代理，只代理人的结婚这一方面。

举个:chestnut:

接口类	

```java
public interface StaticDao {
	//接口
    int insert();
    int delete();
}
```

被代理类

```java
//被代理类专注于做自己的事情
public class StudentDaoImpl implements StaticDao {
    @Override
    public int insert() {
        System.out.println("focus on insert into student ...");
        return 1;
    }
    @Override
    public int delete() {
        System.out.println("focus on delete from student ...");
        return 1;
    }
}
public class UserDaoImpl implements StaticDao {
    @Override
    public int insert() {
        System.out.println("focus on insert into user ...");
        return 0;
    }
    @Override
    public int delete() {
        System.out.println("focus on insert into user ...");
        return 0;
    }
}
```

代理类

```java
public class StaticProxy implements StaticDao {
    //获取被代理对象
    private static StaticDao staticDao;
    public StaticProxy(StaticDao real){
        staticDao = real;
    }
    @Override
    public int insert() {
        System.out.println("proxy working...");
        begin();//开启事务
        staticDao.insert();
        commit();//提交事务
        return 0;
    }
    @Override
    public int delete() {
        System.out.println("proxy working...");
        begin();
        staticDao.delete();
        commit();
        return 0;
    }
    public void begin(){
        System.out.println("开启事务。。。");
    }
    public void commit(){
        System.out.println("结束事务。。。");
    }
}
```

测试类

```java 
@Test
public void staticProxyTest(){
    StudentDaoImpl studentDao = new StudentDaoImpl();
    //这里我们只需要调用代理类即可
    StaticProxy proxy1 = new StaticProxy(studentDao);
    System.out.println(proxy1.insert());
    System.out.println(proxy1.delete());
    
    UserDaoImpl userDao = new UserDaoImpl();
    StaticProxy proxy2 = new StaticProxy(userDao);
    System.out.println(proxy2.insert());
    System.out.println(proxy2.delete());
}
```

以上就是静态代理的工作方式，虽然使用代理可以**降低系统的耦合性**，**增加扩展性**，被代理类只需要关注核心业务的实现，将**通用管理逻辑**与**业务逻辑**分离，**提高代码的复用性**。

但是静态代理只能为固定的一些类做代理，如果我们有很多对象需要被代理，那么就需要写很多个代理类，增加工作量以及代码体量。

#### 动态代理

> 为了解决以上问题，动态代理应运而生，动态代理几乎可以为所有的类产生代理类这样就避免了大量的代码逻辑。
>
> 动态代理有两种实现方式
>
> - JDK动态代理
> - CGLib动态代理
>
> ![image-20220124161217121](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220124161246.png)

##### JDK动态代理

> Java动态代理通过 获取**被代理对象实现的接口** 产生 **代理对象**。
>
> Java动态代理类位于java.lang.reflect包下，InvocationHandler。
>
> ```
> public Object invoke(Object proxy, Method method, Object[] args)
> ```
>
> 即此时是没有代理对象的，代理对象是动态产生的。而想要产生一个匹配的代理对象，需要提前准备好**接口**与**被代理对象**，然后JDK通过被代理对象获取其接口，然后再通过实现接口产生一个代理对象。

接口类

```
public interface StaticDao {
    int insert();
    int delete();
}
```

被代理对象

```java
public class StudentDaoImpl implements StaticDao {

    @Override
    public int insert() {
        System.out.println("focus on insert into student ...");
        return 1;
    }

    @Override
    public int delete() {
        System.out.println("focus on delete from student ...");
        return 1;
    }
}
public class UserDaoImpl implements StaticDao {
    @Override
    public int insert() {
        System.out.println("focus on insert into user ...");
        return 0;
    }

    @Override
    public int delete() {
        System.out.println("focus on insert into user ...");
        return 0;
    }
}
```

获取代理对象

```java
public class JDKDynamicProxy implements InvocationHandler {
    //代理对象
    private final Object real;
    public JDKDynamicProxy(Object obj){
        this.real = obj;
    }

    public Object getProxy(){
        //想要生成代理对象，就需要知道被代理对象的类加载器和接口类
        ClassLoader classLoader= real.getClass().getClassLoader();
        Class<?>[] interfaces = real.getClass().getInterfaces();
        /*
        * 我们需要使用Proxy的newProxyInstance方法，一共需要三个参数
        * 1、ClassLoader，类加载器
        * 2、Class<?>[] interfaces，接口类集合
        * 3、InvocationHandler，调用处理程序，即使用产生的代理对象调用方法时，用于拦截方法执行的处理器
        * */
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    /**
     * 调用方法，并做事务管理
     * @param proxy 代理类
     * @param meth 调用的方法（代理类）
     * @param args 调用方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable {
        begin();
        //调用函数
        // 第一个参数：那个对象调用这个函数
        // 第二个参数，被调用的函数的参数
        Object invoke = meth.invoke(real, args);
        commit();
        return invoke;
    }

    public void begin(){
        System.out.println("开启事务...");
    }

    public void commit(){
        System.out.println("提交事务...");
    }
}

```

测试

```java
public void JDKDynamicProxy() {
    StudentDaoImpl studentDao = new StudentDaoImpl();
    JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(studentDao);
    //获取代理对象
    StaticDao dao = (StaticDao) jdkDynamicProxy.getProxy();
    //代理对象调用方法，会被
    dao.insert();
    dao.delete();
}
```

> JDK动态代理有一个缺点就是被代理对象必须实现了一个接口。
>
> 不然会报错。

##### CGLib动态代理

> 为了纠正上面没实现接口就导致动态代理失效，所以CGLib动态代理应运而生。
>
> 原理相同，只是被代理类不需要实现接口，而是通过继承被代理对象来实现代理。
>
> 但是，CGLib动态代理不能为final类创建代理对象。

添加CGLib依赖

```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
```



接口类

```
public interface StaticDao {
    int insert();
    int delete();
}
```

被代理对象

```java
public class StudentDaoImpl implements StaticDao {

    @Override
    public int insert() {
        System.out.println("focus on insert into student ...");
        return 1;
    }

    @Override
    public int delete() {
        System.out.println("focus on delete from student ...");
        return 1;
    }
}
public class UserDaoImpl implements StaticDao {
    @Override
    public int insert() {
        System.out.println("focus on insert into user ...");
        return 0;
    }

    @Override
    public int delete() {
        System.out.println("focus on insert into user ...");
        return 0;
    }
}
```

获取代理对象

```java
public class CGLibDynamicProxy implements MethodInterceptor {

    private final Object real;
    public CGLibDynamicProxy(Object obj){
        this.real = obj;
    }

    public Object getProxy(){
        //与JDK类似,
        Enhancer enhancer = new Enhancer();
        //设置该类为其子类，被代理类为其父类
        enhancer.setSuperclass(real.getClass());
        //回调函数
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        begin();
        //通过反射调用代理类
        // 第一个参数：那个对象调用这个函数
        // 第二个参数，被调用的函数的参数
        Object returnV = method.invoke(real, objects);
        commit();
        return returnV;
    }

    public void begin(){
        System.out.println("开启事务...");
    }

    public void commit(){
        System.out.println("提交事务...");
    }
}
```



### SpringAOP

> 对代理模式有一个了解后，我们就可以正式进入Spring的AOP学习了。
>
> AOP：Aspect Oriented Programming 面向切面编程，指利用“横切技术”，对原有业务进行拦截，然后再拦截的横切面上添加特定的业务逻辑，对原有业务进行增强。
>
> 概括来说，就是在不改动原有代码的情况下，对业务进行增强。
>
> 利用的技术就是横切，将业务逻辑嵌入。
>
> ![1617008695615](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220124173614.png)

#### 部署

##### 创建工程

略

##### 添加AOP依赖

```xml
<!-- SpringAOP 指的是Spring面向切面编程，需要SpringIoC的支持-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.5.RELEASE</version>
</dependency>
<!--aspects依赖，即AOP的核心组件-->
<dependency>
     <groupId>org.springframework</groupId>
     <artifactId>spring-aspects</artifactId>
     <version>5.2.13.RELEASE</version>
</dependency>
```

##### 创建配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--添加aop规范，才能使用aop相关规则-->
</beans>
```

#### 使用

##### 切面类

```java
public class TransactionManager {

    public void begin(){
        System.out.println("开启事务...");
    }

    public void commit(){
        System.out.println("提交事务...");
    }
}
```

##### XML

> XML需要配置`applicationContext.xml`文件，在这里面配置AOP 的功能。
>
> 配置步骤
>
> - 创建切面类，在切面类中定义切点方法
> - 将切面类配置给Spring容器
> - 声明切入点
> - 配置AOP通知策略

```xml
<!--声明切面类，即切入点前后需要配置的类-->
<bean id="transactionManager" class="com.marshio.aop.utils.TransactionManager"/>

<!--配置增强功能（AOP）-->
<aop:config>
    <!--声明切入点（需要被事务管理的类），切入点是业务代码，比如一个提交业务，或者一个新增业务。-->
    <!--
       execution(* com.marshio.aop.dao.*.*(..))
       第一个参数 * 代表的是返回类型，不限制
       第二个参数 com.marshio.aop.dao.*.*(..) 代表的是dao包下面的所有类的所有方法的所有参数类型
    -->
    <aop:pointcut id="allDao" expression="execution(* com.marshio.aop.dao.*.*(..))"/>

    <!--声明切面类方法-->
    <aop:aspect ref="transactionManager" >
        <!--通知-->
        <aop:before method="begin" pointcut-ref="allDao"/>
        <aop:after method="commit" pointcut-ref="allDao"/>
    </aop:aspect>
</aop:config>
```

测试

```java
@Test
public void testXML() {
    ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    StaticDao userDao = (StaticDao) app.getBean("userDao");
    userDao.insert();
}
```

##### 切入点声明

```xml
<!--使用aop:pointcut标签声明切入点：切入点可以是一个方法-->
<aop:pointcut id="book_insert" expression="execution(* com.qfedu.dao.BookDAOImpl.insert())"/>

<!--BookDAOImpl类中所有无参数无返回值的方法-->
<aop:pointcut id="book_pc1" expression="execution(void com.qfedu.dao.BookDAOImpl.*())"/>

<!--BookDAOImpl类中所有无返回值的方法-->
<aop:pointcut id="book_pc2" expression="execution(void com.qfedu.dao.BookDAOImpl.*(..))"/>

<!--BookDAOImpl类中所有无参数的方法-->
<aop:pointcut id="book_pc3" expression="execution(* com.qfedu.dao.BookDAOImpl.*())"/>

<!--BookDAOImpl类中所有方法-->
<aop:pointcut id="book_pc4" expression="execution(* com.qfedu.dao.BookDAOImpl.*(..))"/>

<!--dao包中所有类中的所有方法-->
<aop:pointcut id="pc5" expression="execution(* com.qfedu.dao.*.*(..))"/>

<!--dao包中所有类中的insert方法-->
<aop:pointcut id="pc6" expression="execution(* com.qfedu.dao.*.insert(..))"/>

<!--所有类方法-->
<aop:pointcut id="pc7" expression="execution(* *(..))"/>
```

##### 通知策略

> AOP通知策略：就是声明将切面类中的切点方法如何植入到切入点
>
> - before
> - after
> - before-throwing
> - after-throwing
> - around

```xml
<bean id="transactionManager" class="com.marshio.aop.utils.TransactionManager"/>
<aop:config>
    <!--使用aop:pointcut标签声明切入点：切入点可以是一个方法-->
    <aop:pointcut id="book_insert" expression="execution(* com.qfedu.dao.BookDAOImpl.insert())"/>

    <aop:aspect ref="transactionManager">
        <!--aop:before 前置通知，切入到指定切入点之前-->
        <aop:before method="method1" pointcut-ref="book_insert"/>
        <!--aop:after 后置通知，切入到指定切入点之后-->
        <aop:after method="method2" pointcut-ref="book_insert"/>
        <!--aop:after-throwing 异常通知，切入点抛出异常之后-->
        <aop:after-throwing method="method3" pointcut-ref="book_insert"/>
        <!--aop:after-returning 方法返回值返回之后，对于一个Java方法而言return返回值也是方法的一部分
             因此“方法返回值返回之后”和“方法执行结束”是同一个时间点，随意after 和 after-returning根据配置
             的顺序决定执行顺序-->
        <aop:after-returning method="method4" pointcut-ref="book_insert"/>
        <aop:around method="method5" pointcut-ref="book_insert"/>
    </aop:aspect>
</aop:config>
```



##### 注解

**更新配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--添加context、aop规范-->

    <!-- 声明context使用注解配置 -->
    <context:annotation-config/>

    <!-- 声明Spring工厂注解的扫描范围 -->
    <context:component-scan base-package="com.marshio.aop.pojo"/>

    <!-- 基于注解配置的aop代理-->
    <aop:aspectj-autoproxy/>
</beans>
```

**使用**

```java
package com.marshio.aop.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author masuo
 * @data 25/1/2022 上午8:21
 * @Description 注解式事务管理 --- 虽然是注解类，但是步骤都是一样的
 * Component：表示将此类交给Spring容器管理，是一个对象的意思
 * Aspect：表示这个类是一个切面类
 */
@Component
@Aspect
public class TransactionManagerOnAnnotation {

    //声明切入点
    @Pointcut("execution(* com.marshio.aop.dao.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void begin() {
        System.out.println("开启事务...");
    }

    @After("pointCut()")
    public void commit() {
        System.out.println("提交事务...");
    }

    @AfterThrowing("pointCut()")
    public void afterThrow() {
        System.out.println("事务回滚。。。");
    }

    @AfterReturning("pointCut()")
    public void afterReturn() {
        //AfterReturning == After , 因为方法返回值返回之后，对于一个Java方法而言return返回值也是方法的一部分
        System.out.println("提交事务。。。");
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) {
        System.out.println("");
        long begin = System.currentTimeMillis();
        Object o = null;
        try {
            o = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long commit = System.currentTimeMillis();
        System.out.println("time:"+(commit-begin));
        return o;
    }
}
```

**测试**

```java
public class AnnotationDemo {

    @Test
    public void testAnnotation() {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        StaticDao userDao = (StaticDao) app.getBean("userDao");
        userDao.insert();
    }
}
```



## Spring MVC

### MVC模式

> Spring MVC是一个基于Spring的实现了MVC设计模式轻量级Web框架，实现了Model，View，Controller分离，将web层进行解耦，把复杂的web应用分成逻辑清晰的几部分，简化开发，减少出错，方便组内开发人员之间的配合。
>
> 在 Web 项目的开发中，能够及时、正确地响应用户的请求是非常重要的。用户在网页上单击一个 URL 路径，这对 Web 服务器来说，相当于用户发送了一个请求。而获取请求后如何**解析**用户的输入，并**执行相关处理逻辑**，最终**跳转**至正确的页面**显示反馈结果**，这些工作往往是控制层（Controller）来完成的。
>
> 在请求的过程中，用户的信息被封装在 User 实体类中，该**实体类**在 Web 项目中属于数据模型层（Model）。
>
> 在请求显示阶段，跳转的结果**网页**就属于视图层（View）。
>
> 像这样，控制层负责前台与后台的交互，数据模型层封装用户的输入输出数据，视图层选择恰当的视图来显示最终的执行结果，这样层次分明的软件开发和处理流程被称为MVC模式。

![image-20220125102832124](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220125102833.png)



**总结**：

- 视图层（View）：负责格式化数据并把它们呈现给用户，包括数据展示、用户交互、数据验证、界面设计等功能。
- 控制层（Controller）：负责接收并转发请求，对请求进行处理后，指定视图并将响应结果发送给客户端。
- 数据模型层（Model）：模型对象拥有最多的处理任务，是应用程序的主体部分，它负责数据逻辑（业务规则）的处理和实现数据操作（即在数据库中存取数据）。

> SUN公司在推出JSP技术的同时，也推出了两种web应用程序的开发模式。
>
> - **JSP+JavaBean**
> - **JSP+Servlet+JavaBean**

**JSP+JavaBean**

> 在此开发模式中，JSP处理用户请求，JavaBean封装和处理用户数据。该模式只有Model和View层，一般View层会处理控制层的工作。由于其处理模式导致其耦合度较高，只适合处理小型简单逻辑网站。
>
> 图源：http://c.biancheng.net/spring_mvc/mvc.html

![JSP+JavaBean](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220208113434.png)

通过上图可以发现 JSP 从 `HTTP Request`（请求）中获得所需的数据，并进行业务逻辑的处理，然后将结果通过 `HTTP Response`（响应）返回给浏览器。从中可见，JSP+JavaBean 模式在一定程度上实现了 MVC，即 JSP 将控制层和视图合二为一，JavaBean 为模型层。

JSP+JavaBean 模式中 JSP 身兼数职，既要负责视图层的数据显示，又要负责业务流程的控制，结构较为混乱，并且也不是我们所希望的松耦合架构模式，所以当业务流程复杂的时候并不推荐使用。

**JSP+Servlet+JavaBean**

> 结构清晰，系统松耦合，但是结构复杂，不适合中小型网站设计。

![Servlet+JSP+JavaBean](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220208113638.png)

相比 JSP+JavaBean 模式来说，Servlet+JSP+JavaBean 模式将控制层单独划分出来负责业务流程的控制，接收请求，创建所需的 JavaBean 实例，并将处理后的数据返回视图层（JSP）进行界面数据展示。

Servlet+JSP+JavaBean 模式的结构清晰，是一个松耦合架构模式，一般情况下，建议使用该模式。

#### 优缺点

**优点**

- 多视图共享一个模型，大大提高代码的复用
- 松耦合
- 控制器提高了应用程序的灵活性和可配置性
- 有利于软件工程化管理

**缺点**

- 逻辑复杂
- 系统结构复杂，实现难度增加
- 视图对模型数据访问效率低，因为需要经过controller层

### Spring MVC

> Spring MVC 是 Spring 提供的一个基于 MVC 设计模式的轻量级 Web 开发框架，本质上相当于 Servlet。
>
> Spring MVC 是结构最清晰的 Servlet+JSP+JavaBean 的实现，是一个典型的教科书式的 MVC 构架，不像 Struts 等其它框架都是变种或者不是完全基于 MVC 系统的框架。
>
> Spring MVC 角色划分清晰，分工明细，并且和 Spring 框架无缝结合。Spring MVC 是当今业界最主流的 Web 开发框架，以及最热门的开发技能。
>
> 在 Spring MVC 框架中，Controller 替换 Servlet 来担负控制器的职责，用于接收请求，调用相应的 Model 进行处理，处理器完成业务处理后返回处理结果。Controller 调用相应的 View 并对处理结果进行视图渲染，最终客户端得到响应信息。
>
> Spring MVC 框架采用松耦合可插拔的组件结构，具有高度可配置性，比起其它 MVC 框架更具有扩展性和灵活性。
>
> 此外，Spring MVC 的注解驱动和对 REST 风格的支持，也是它最具特色的功能。无论是在框架设计，还是扩展性、灵活性等方面都全面超越了 Struts2 等 MVC 框架。并且由于 Spring MVC 本身就是 Spring 框架的一部分，所以可以说与 Spring 框架是无缝集成，性能方面具有先天的优越性，对于开发者来说，开发效率也高于其它的 Web 框架，在企业中的应用越来越广泛，成为主流的 MVC 框架。

#### 优点

- 清晰地角色划分，Spring MVC 在 Model、View 和 Controller 方面提供了一个非常清晰的角色划分，这 3 个方面真正是各司其职，各负其责。
- 灵活的配置功能，可以把类当作 Bean 通过 XML 进行配置。
- 提供了大量的控制器接口和实现类，开发者可以使用 Spring 提供的控制器实现类，也可以自己实现控制器接口。
- 真正做到与 View 层的实现无关。它不会强制开发者使用 JSP，可以根据项目需求使用 Velocity、FreeMarker 等技术。
- 国际化支持
- 面向接口编程
- 与 Spring 框架无缝集成

### 搭建

#### 依赖

```xml
<!-- 设置打包方式为war包 -->
<packaging>war</packaging>

<dependencies>
    <!-- spring-webmvc包含spring基础组件以及spring-web组件,此即MVC的Model层 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.9.RELEASE</version>
    </dependency>

    <!-- servlet-api，即MVC的Controller层 -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
    </dependency>

    <!-- thymeleaf,即MVC的View层 -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf-spring5</artifactId>
        <version>3.0.11.RELEASE</version>
    </dependency>
</dependencies>
```

#### 配置

> 在SpringMVC项目中，需要配置前端控制器，还需要配置SpringMVC配置文件。
>
> - web.xml，前端控制器，过滤等
> - SpringMVC配置文件，相当于Spring中的`applicatContext.xml`配置文件

##### 前端控制器

> Spring MVC 是基于`Servlet`的，`DispatcherServlet`是整个SpringMVC框架的核心，因为几乎所有的请求都需要经过它分配给相应的`Controller`，所以配置SpringMVC首先需要配置`DispatcherServlet`。

##### MVC配置文件

> 此时，Spring不只是需要解析控制层和模型层文件，还需要解析视图层文件，我们需要告诉Spring如何解析视图层文件。

- **默认配置方式**

> Spring MVC 初始化时将在应用程序的 WEB-INF 目录下查找配置文件，该配置文件的命名规则是`${servletName}-servlet.xml`，例如下面的`servlet-name`标签的值是`springmvc`，那么配置文件的名称就是`springmvc-servlet.xml`。

`web.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- 部署DispatcherServlet -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <!-- SpringMVC提供了一个DispatcherServlet的类（SpringMVC前端控制器），
			 用于拦截用户请求，并将请求交给SpringMVC处理 -->
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 由于这是静态资源，所以在启动时不会加载，这就导致第一次访问服务器时会很慢，为了解决这个问			  题，就需要使用下面的标签设置为1，表示在启动容器时也一同启动 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!-- 表示拦截所有请求，除.jsp请求，因为.jsp相当于一个servlet，而在SpringMVC中，servlet需要经过特殊的处理       -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

`springmvc-servlet.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- 扫描控制器，其实应该扫描全包，目前实验只扫描控制器包 -->
    <context:component-scan base-package="com.marshio.mvc.controller"/>
    
    <!-- 配置视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <!-- order数字越大，优先级越高 -->
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/templates/"/>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
   
</beans>
```

- **常用注解配置方式**

> 由于在Spring中，我们将所有的配置资源文件都放在resource下面了，所以上面的默认配置不符合我们的需求，而为了迎合这一需求，我们在配置`***-servlet.xml`时，可以根据`<init-param>`标签指定配置文件的位置。

`web.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!-- 指定Springmvc-servlet.xml的位置 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!-- 这个名字可以自己命名，不在遵循默认规则 -->
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

`springmvc-servlet.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 扫描控制器，其实应该扫描全包，目前实验只扫描控制器包 -->
    <context:component-scan base-package="com.marshio.mvc.controller"/>
    
    <!-- 配置视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <!-- order数字越大，优先级越高 -->
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/templates/"/>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

</beans>
```

- **常用XML配置方式**

> XML较复杂，

### 使用

> 同其他Spring项目，SpringMVC提供了注解和配置XML文件两种方式。

举个:chestnut:

#### 注解版

>  注解版需要在SpringMVC配置文件中声明需要被扫描的包。
>
> 还需要说明视图解析方式。

**项目结构**

![image-20220209110829308](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220209110829.png)

**SpringMVC配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 扫描 -->
    <context:component-scan base-package="com.marshio.mvc.controller"/>

    <!-- 配置视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/templates/"/>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

</beans>
```

> 注解版最常用，也最简单，无需写大量的复杂的xml文件，只需一个***Controller.class就能解决问题。
>
> **注意**
>
> - 需要使用`@Controller`注解表明这是一个控制器类
> - 需要使用`@RequestMapping`注解表明请求路径
> - 方法上需要使用`@RequestMapping(“url”)`声明当前方法请求的url

```java
//声明为控制器类
@Controller
public class LoginController {
    /**
     * 访问 /  ==》 就是访问  /templates/ + index + .html，即/templates/index.html
     * @return index.html
     */
    @RequestMapping(value = "/")
    public String login(){
        return "index";
    }
}
```

**配置Tomcat服务器**

![image-20220209110538375](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220209110538.png)

![image-20220209110701417](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220209110701.png)

**启动Tomcat**

![image-20220209110740815](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220209110740.png)

### 执行流程

#### SpringMVC组件

> - **DispatcherServlet**：前端控制器，拦截所有的请求并处理请求，相当于中央处理器，进行统一调度。
> - **HandlerMapping**：处理器映射，在我的理解中就是，你写的@RequestMapping注解，会被整合成一张地图（map），前端的请求被DispatcherServlet拦截后，DispatcherServlet会拿着你的请求路径来这里问该去找哪个处理器，HandlerMapping会告诉他一个处理器信息。
> - **HandlerAdapter**：这个就是你找到处理器之后，怕你不适配处理器，就类似，你是iso的充电口，而人家只提供typec的口，就是为了能让你用上这个处理器
> - **Handler**：类似servlet
> - **ViewResolver**：视图解析器

![Spring MVC执行流程](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220209112548.png)

> 执行流程如下
>
> 1. 用户发起请求，请求被服务端的`DispatcherServlet`拦截。
> 2. DispatcherServlet会去查找HandlerMapping，并会返回一个HandlerExecutionChain（执行链）。
> 3. DispatcherServlet会将HandlerExecutionChain中的Handler信息发送给HandlerAdapter。
> 4. HandlerAdapter会根据Handler信息找到并执行相应的Handler（又称Controller）
> 5. Handler执行完毕会返回给HandlerAdapter一个ModelAndView对象，此对象包括Model 数据模型以及View 视图信息，此时视图还没有被渲染。
> 6. HandlerAdapter接收到ModelAndView后会将其返回给DispatcherServlet。
> 7. DispatcherServlet接收到后会请求ViewResolver解析视图信息。
> 8. ViewResolver根据View信息匹配到相应的视图信息，并返回给DispatcherServlet。
> 9. DispatcherServlet接收到具体的View视图后，进行视图渲染，将Model中的模型数据填充到View视图中的request域生成最终的View。
> 10. 视图负责将结果显示到浏览器。

### 常用注解

#### Controller

> 声明该类是一个控制器类。
>
> SpringMVC使用扫描机制找到应用中所有基于注解的控制器类，所以，为了能让控制器类被SpringMVC扫描到，需要在配置文件中声明spring-context，并使用<context:component-scan/>标签指定扫描范围，需要将所有的控制器类放在该包下。

举个:chestnut:

```java
package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;

/**
 * @author masuo
 * @data 9/2/2022 上午9:40
 * @Description 登陆控制器
 * 想要将这个控制器交给Spring有两种方式
 * 1.注解+扫描,想要此控制器被扫描到，还需要配置
 * 2.配置bean
 */

@Controller
public class LoginController {

    public String login(){
        return "index";
    }

}
```

```xml
<!-- 扫描 -->
<context:component-scan base-package="com.marshio.mvc.controller"/>
```

#### RequestMapping

> 一个控制器内有多个处理请求的方法，每个方法负责不同的请求操作，RequsetMapping就负责将请求映射到对应的方法上。
>
> 此注解可以用在类上或方法上。
>
> - 作为类注解：表示类中所有的请求的父路径都是它
> - 作为方法注解：映射路径
>
> RequsetMapping有以下属性

##### value

> 默认属性，如果只有 value 属性时，可以省略该属性名，如果有其它属性，则必须写上 value 属性名称。
>
> 支持通配符匹配。 @RequestMapping(value="toUser/*") 表示 http://localhost:8080/toUser/1 或 http://localhost:8080/toUser/hahaha 都能够正常访问.
>
> 注意，此值是唯一的。

```java
@RequestMapping(value="toUser")
@RequestMapping("toUser")
@RequestMapping(value="toUser/*")
```

##### path

> 等同于value。

##### method

> 表示该方法支持的HTTP请求，默认支持所有。
>
> 对于指定请求方式的控制器方法，SpringMVC提供了派生注解
>
> 处理get请求的映射-->@GetMapping
>
> 处理post请求的映射-->@PostMapping
>
> 处理put请求的映射-->@PutMapping
>
> 处理delete请求的映射-->@DeleteMapping
>
> 但是目前的浏览器只支持get和post请求，若设置了其他请求方式（put和delete），则按照默认的get请求处理。

```java
@RequestMapping(value = "toUser",method = RequestMethod.GET) 
//表示该方法只支持 GET 请求。

//也可指定多个 HTTP 请求
@RequestMapping(value = "toUser",method = {RequestMethod.GET,RequestMethod.POST})
//说明该方法同时支持 GET 和 POST 请求。
```

##### params

> 指定请求中规定的参数。

举个:chestnut:

```java
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "login",params = "exists")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "logout",params = "status=1")
    public String logout(){
        return "logout";
    }
}
```

那么在访问时，我们需要如下

```html
<div>
    <a th:href="${'user/login?exists=1'}">登录</a><br>
</div>
<div>
    <a th:href="${'user/logout?status=1'}">登出</a><br>
</div>
```

##### headers



##### name

> 相当于方法注释



### 获取请求参数

> 获取请求参数即将前端封装的数据发送到后端，后端如何获取的问题。
>
> 在SpringMVC中有多种获取请求参数的方法，
>
> - 通过ServletAPI中的HttpServletRequest接受请求参数
> - 通过实体Bean接收请求参数
> - 通过处理方法的形参接受请求参数
> - 通过@PathVariable接收URL中的请求参数
> - 通过@RequestParam接受请求参数
> - 通过@ModelAttribute接收请求参数

#### ServletAPI

> ServletAPI是非常古老的一个方法。
>
> 但是他不能获取数组数据，如有一个字段是多选值，通过`request.getParameter(“paramName”)`获取到的只能是第一个数据。

##### 请求

```html
<a th:href="@{testGetParamMethod1(username='ms',password=123456)}">Servlet API</a>
```

##### 后端

```java
/**
 * 获取参数方法1--ServletAPI
 * 由于SpringMVC中的所有请求都会经过DispatcherServlet，
 * 而经过DispatcherServlet处理的请求都会被封装起来，而被封装的数据里面就包含了传递的参数
 * 我们只需要找到他们就可以了
 *
 * @param request DispatcherServlet会给此形参自动赋值
 * @return params
 */
@RequestMapping(value = "testGetParamMethod1")
public String testGetParamMethod1(HttpServletRequest request) {
    //这个在检测到参数为HttpServletRequest，则表示这是一个当前请求
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println("username:" + username + ",password:" + password);
    return "success";
}
```

##### 输出

![image-20220210090056516](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210090056.png)

#### 通过形参获取参数

> 类似于通过ServletAPI获取参数。
>
> 能够获取数组数据，可以通过数组接收，也可以直接接收。

##### 请求

```html
<a th:href="@{testGetParamMethod2(username='ms',password=123456)}">形参直接获取</a>
<!--http://localhost:8080/testGetParamMethod2?username=ms&password=123-->
<!--http://localhost:8080/testGetParamMethod2?username=ms&password=123&sex=1-->
```

##### 后端

```java
//传递的参数有重复时---直接获取
@RequestMapping(value = "testGetParamMethod2")
public String testGetParamMethod2(String username,String password,String sex) {
    //使用形参获取参数时，DispatcherServlet会自动注入参数值
    System.out.println("username:" + username + ",password:" + password+",sex:"+sex);
    return "success";
}

//传递的参数有重复时---数组接收
@RequestMapping(value = "testGetParamMethod2")
public String testGetParamMethod2(String username,String password,String[] sex) {
    //使用形参获取参数时，DispatcherServlet会自动注入参数值
    System.out.println("username:" + username + ",password:" + password+",sex:"+sex);
    return "success";
}
```

##### 输出

![image-20220210091215387](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210091215.png)



#### @RequestParam

> @RequestParam是将请求参数和控制器方法的形参创建映射关系。
>
> 一共有四个属性
>
> - **value**：参数名称，
> - **name**：参数名称，等同于value，常用value
> - **required**：参数是否是必需的，默认为true，如果为true，则必须传递参数值，如果没有参数值，且没有设置`defaultValue`则会报400错误。
> - **defaultValue**：默认值，不管required属性值为true或false，当value所指定的请求参数没有传输或传输的值为""时，则使用默认值为形参赋值，其默认值是16个unicode字符，其唯一目的是从不匹配用户声明的值。

##### 请求

```html
<a th:href="@{testGetParamMethod3(username='ms',password=123456)}">requestParam</a>
<!--可以在发送第一次数据后，自己拼接请求测试-->
```



##### 后端

```java
@RequestMapping(value = "testGetParamMethod3")
public String testGetParamMethod3(
    @RequestParam(value = "username",required = false)String username,
    @RequestParam(value = "password",defaultValue = "hha")String password,
    @RequestParam(value = "sex",required = false,defaultValue = "hha")String sex) {
    System.out.println("username:" + username + ",password:" + password+",sex:"+sex);
    return "success";
}
```



##### 输出

![image-20220210094421143](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210094421.png)

#### @RequestHeader

> 类似于RequestParam注解，只是此注解是用于获取请求头的。
>
> 用法同[@RequestParam](# @RequestParam)。

##### 后端

```java
@RequestMapping(value = "testGetParamMethod4")
public String testGetParamMethod4(
    @RequestHeader(value = "host") String host,
    @RequestHeader(value = "User-Agent") String userAgent,
    @RequestHeader(value = "Accept") String accept,
    @RequestHeader(value = "Accept-Encoding") String acceptEncoding,
    @RequestHeader(value = "Connection") String connection) {
    System.out.println("host:" + host);
    System.out.println("User-Agent:" + userAgent);
    System.out.println("Accept" + accept);
    return "success";
}
```

##### 可获取的参数

![image-20220210100023260](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210100023.png)

#### @CookieValue

> 类似于RequestParam注解，只是此注解是用于获取`Cookie`的。
>
> 用法同[@RequestParam](# @RequestParam)。

##### Cookie

> 首先，我们先来了解一下什么是cookie。
>
> Cookie其实就是一小段文本信息，用来确认用户身份的，保证多次请求是相关的。
>
> 谈到Cookie，就不得不说一下它的弟弟Session，详情请看[Session](# Session)。

**产生原因**

> 我们为什么需要cookie呢？
>
> 因为我们的Web应用程序联网是用的传输协议是HTTP，而HTTP协议是无状态传输。
>
> 无状态即无记忆性，指服务器的每个请求都是完全独立的，这就导致了我们访问一次之后，再次访问时，他不认识我们了，需要重新建立连接。
>
> 举个例子
>
> 摸鱼时我想要访问淘宝，请求淘宝的地址，输入用户名密码，访问成功，访问一段时间后，我关了浏览器，过一段时间，我又想摸鱼了，又是访问淘宝，此时淘宝服务器又要我输入用户名密码，这让人很是生气。所以决定不在摸鱼了。

**解决方法**

> 像以上的场景在以前还是很常见的，这就是需求啊，所以人们就想了一个办法---验证身份。
>
> 在第一次访问时，服务器会验证你的身份，发现你没有身份证，那好，给你发一个身份证，你需要保存好。
>
> 你再来访问的时候带上身份证，此时服务器验证你的身份，发现你有了身份证，你就可以正常通行了。
>
> 终于可以快乐的摸鱼了。由于摸鱼过于频繁，你被fire了。
>
> **过程**
>
> 客户端请求服务器，如果服务器需要记录该用户状态，就使用response向客户端浏览器颁发一个Cookie。客户端浏览器会把Cookie保存起来。当浏览器再请求该网站时，浏览器把请求的网址连同该Cookie一同提交给服务器。服务器检查该Cookie，以此来辨认用户状态。服务器还可以根据需要修改Cookie的内容。

**跨域性**

> 由于cookie是服务器给客户端发送的，所以淘宝的cookie只能用在淘宝上，而不能用在京东。所以cookie是不具备跨域性的。
>
> Cookie在客户端是由浏览器来管理的。浏览器能够保证淘宝只会操作淘宝的Cookie而不会操作京东的Cookie，从而保证用户的隐私安全。浏览器判断一个网站是否能操作另一个网站Cookie的依据是域名。

**属性**

| param   | type    | desc                                                         |
| ------- | ------- | ------------------------------------------------------------ |
| name    | String  | cookie的名称，不可更改                                       |
| value   | Object  | cookie的值，如果值为Unicode字符，需要为字符编码。如果值为二进制数据，则需要使用BASE64编码 |
| maxAge  | int     | 存活时间，如果为正数，则该Cookie在maxAge秒之后失效。如果为负数，该Cookie为临时Cookie，关闭浏览器即失效，浏览器也不会以任何形式保存该Cookie。如果为0，表示删除该Cookie。默认为–1。单位为秒（Second）。 |
| secure  | boolean | 是否仅被安全协议传输，默认为false。                          |
| path    | String  | cookie的使用路径，如果设置为“/sessionWeb/”，则只有contextPath为“/sessionWeb”的程序可以访问该Cookie。如果设置为“/”，则本域名下contextPath都可以访问该Cookie。注意最后一个字符必须为“/” |
| domain  | String  | 可以访问该cookie的域名                                       |
| comment | String  | 注释                                                         |
| version | int     | Cookie使用的版本号。0表示遵循Netscape的Cookie规范，1表示遵循W3C的RFC 2109规范 |

##### 后端

```java
@RequestMapping(value = "testGetParamMethod5")
public String testCookieValue(
    @CookieValue(value = "newcookie",required = false) Cookie cookie) {
    //需要在保证已经有了cookie的情况下这么用
    System.out.println("host:" + cookie.getValue());
    return "success";
}
```

#### Bean实体获取

##### 请求

> 注意，想要直接使用POJO类接收参数，那么参数的name属性的值就必须和POJO一一对应，可以为空。

```html
<!--post方法-->
<div>
    <form th:action="@{/testGetParamMethod6}" method="post">
        名称：<label><input type="text" name="name"></label><br>
        密码：<label><input type="text" name="pwd"></label><br>
        性别：<label><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女</label><br>
        年龄：<label><input type="text" name="age"></label><br>
        爱好：<label>
                <input type="checkbox" value="1" name="hobby">游戏
                <input type="checkbox" value="2" name="hobby">看书
                <input type="checkbox" value="3" name="hobby">编程
             </label><br>

        <input type="submit">
    </form>
</div>
<hr>
<!--get方法-->
<div>
    <form th:action="@{/testGetParamMethod6}" method="get">
        名称：<label><input type="text" name="name"></label><br>
        密码：<label><input type="text" name="pwd"></label><br>
        性别：<label><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女</label><br>
        年龄：<label><input type="text" name="age"></label><br>
        爱好：<label>
        <input type="checkbox" value="1" name="hobby">游戏
        <input type="checkbox" value="2" name="hobby">看书
        <input type="checkbox" value="3" name="hobby">编程
    </label><br>

        <input type="submit">
    </form>
</div>
```

##### 后端

> 使用POJO获取参数，需要先建立POJO类。然后直接用该类接收参数即可。

```java
public class User {
    String name;
    String pwd;
    String sex;
    int age;
    String []hobby;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", hobby=" + Arrays.toString(hobby) +
                '}';
    }

    public User() {
    }

    public User(String name, String pwd, String sex, int age, String[] hobby) {
        this.name = name;
        this.pwd = pwd;
        this.sex = sex;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }
}
```

`ParamController.java`

```java
@RequestMapping(value = "testGetParamMethod6")
public String testPojo(User user) {
    //直接用对象接收即可
    System.out.println("user:" + user.toString());
    return "success";
}
```

##### 输出

![image-20220210153056442](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210153056.png)

#### 乱码问题

> 从上面可以看到，当遇到汉字请求时，会有很多乱码，Servlet乱码分为Request乱码和Response乱码。Request乱码又分get请求乱码和post请求乱码。
>
> get请求乱码可以一次性解决。
>
> post请求乱码需要给servlet加编码格式

##### Response乱码

> https://www.cnblogs.com/opaljc/p/3807786.html

##### GET乱码

> 我们需要知道，GET请求乱码的原因是由于tomcat的编码没有统一，所以需要更改tomcat的设置。
>
> 找到tomcat安装路径--》conf，打开server.xml
>
> ```xml
> <Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
> ```
>
> 改成下面
>
> ```xml
> <Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" URIEncoding="UTF-8"/>
> ```
>
> 

##### POST乱码

> 在老版的Servlet中，我们经常使用`request.setCharacterEncoding(“UTF-8”)`来设置字符编码。但是在SpringMVC中，由于所有请求都被Dispatcher Servlet拦截，此时我们在设置字符编码是没用的，因为他已经获取了request对象。
>
> 此时我们就需要在DispatcherServlet启动之前给他设置编码。

`web.xml`

> 来看一下源码
>
> ```java
> @Override
> protected void doFilterInternal(
>     HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
>     throws ServletException, IOException {
> 	//我们需要设置encoding
>     String encoding = getEncoding();
>     if (encoding != null) {
>         if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
>             //由于我们已经设置了encoding，所以requset一定会被设置为UTF-8
>             request.setCharacterEncoding(encoding);
>         }
>         if (isForceResponseEncoding()) {
>             //此处我们需要设置forceResponseEncoding
>             response.setCharacterEncoding(encoding);
>         }
>     }
>     filterChain.doFilter(request, response);
> }
> ```
>
> 所以，综合源码，我们只需要设置`encoding`和`forceResponseEncoding`即可。

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <!-- 设置字符编码为UTF-8 -->
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <!-- 强制转换response字符编码 -->
        <param-name>forceResponseEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <!-- 对所有的请求设置字符编码 -->
    <url-pattern>/</url-pattern>
</filter-mapping>
```

### 域对象共享数据

> 使用域对象共享数据，即在后端将处理好的数据发送到前端或其他控制器，前端接收数据的过程。
>
> 在SpringMVC中有多种域对象
>
> - **Request**域
>     - 使用**ServletAPI**向**request**域对象共享数据
>     - 使用**ModelAndView**向**request**域对象共享数据
>     - 使用**Model**向**request**域对象共享数据
>     - 使用**Map**向**request**域对象共享数据
>     - 使用**ModelMap**向**request**域对象共享数据
> - **Session**域
>     - 向**Session**域对象共享数据
> - **Application**域
>     - 向**Application**域对象共享数据

#### Request域

> 向request域对象发送数据，request域在一次请求的生命周期内有效。

**请求**

> 请求基本都是一致的，我们只需要更改访问地址即可。
>
> 注意，访问地址需要以 / 开头，

```html
<!-- 访问如下 -->
<a th:href="@{/servletApi(username='ms',password=123456)}">Servlet API</a>
```



**接收数据**

> 我们先写好接收数据的模板，这个模板对后面所有请求都有效。我们一共传递三个数据。

```html
<div>
    <!-- 在这里，idea可能会报红，但是注意，这不是错误 -->
    <p th:text="'message='+${msg}"></p>
    <p th:text="'username='+${username}"></p>
    <p th:text="'password='+${password}"></p>
</div>
```



##### 使用ServletAPI共享数据

**接收并转发数据**

```java
@RequestMapping("servletApi")
public String sendMsgByServletApi(HttpServletRequest request){
    //前端页面获取Request域对象的属性值时，只需要使用${attributeName}就可获取
    request.setAttribute("msg","servlet msg");
    request.setAttribute("username",request.getParameter("username"));
    request.setAttribute("password",request.getParameter("password"));
    return "success";
}
```



##### 使用ModelAndView共享数据

```java
@RequestMapping("modelAndView")
public ModelAndView sendMsgByModelAndView(String username,String password){
    ModelAndView mav = new ModelAndView();
    //前端页面获取Request域对象的属性值时，只需要使用${attributeName}就可获取
    mav.addObject("msg","modelAndView msg");
    mav.addObject("username",username);
    mav.addObject("password",password);
    mav.setViewName("success");
    return mav;
}
```



##### 使用Model共享数据

```java
@RequestMapping("model")
public String sendMsgByModel(String username,String password,Model model){
    model.addAttribute("msg","model msg");
    model.addAttribute("username",username);
    model.addAttribute("password",password);
    return "success";
}
```



##### 使用Map共享数据

```java
@RequestMapping("map")
public String sendMsgByMap(String username, String password, Map<String, Object> map){
    map.put("msg","map msg");
    map.put("username",username);
    map.put("password",password);
    return "success";
}
```



##### 使用ModelMap共享数据

```java
@RequestMapping("modelMap")
public String sendMsgByModelMap(String username, String password, ModelMap modelMap){
    modelMap.addAttribute("msg","modelMap msg");
    modelMap.addAttribute("username",username);
    modelMap.addAttribute("password",password);
    return "success";
}
```



#### Session域

> 向session域对象发送数据，session域对象的范围在此次对话都有效，其范围大于request域，小于application域。

##### Session

> 再向Session域对象共享数据之前，我们先来简单得了解以下什么是Session。
>
> Session表面意思是一次会话，相对于浏览器来说，就是浏览器的打开到结束，就是一个Session，中间页面跳转是不会丢失`Session`数据的。
>
> 如果你看过之前的[Cookie](# Cookie)，你就知道，HTTP协议是无状态协议。而如果客户端禁用了Cookie功能，那么Cookie将不再生效，与此同时，依赖于Cookie的Session也不再生效。
>
> 因为服务端会向客户端发送一个名为`JSESSIONID`的Cookie，它的值就是该Session的ID，Session依据该Cookie来识别是否为同一用户。所以，本质上来讲，session也是一个cookie，当然不同的服务器实现session的方法不同，所以有时禁用Cookie，session照样能够使用。
>
> Session将信息保存在服务器端，客户端通过SessionID获取对应的Session，这样减轻了客户端的压力，但是增加了服务器端的压力。
>
> 上面说Cookie是通过检查“身份证”来查验客户端的身份，那么Session就是通过“客户表”来查验客户端的身份。

##### 生命周期

> Session保存在服务器端，一般存放在服务器的内存中，每个用户拥有一个独立的Session，如果Session过于复杂，会造成服务器内存溢出。
>
> 为防止内存溢出，服务器会把长时间没有活跃的Session从内存删除，即超时时间。在tomcat中，超时时间默认为20分钟。
>
> Session在用户第一次访问服务器的时候自动创建。注意，只有访问JSP、Servlet等程序才会创建Session，只访问HTML、IMAGE等静态资源是不会创建Session的，但是可以使用`request.getSession(true)`强制生成session。
>
> Session生成后，只要用户继续访问，服务器就会更新Session的最后访问时间，并维护该Session。只要用户访问一次服务器，无论是否读写Session，服务器都认为用户活跃（active）了一次。

```java
@RequestMapping("session")
public String sendMsgBySession(String username,String password, HttpSession session){
	//向session域对象中存放数据
    session.setAttribute("msg","session msg");
    session.setAttribute("username",username);
    session.setAttribute("password",password);
    return "success";
}
```

**取值**

```html
<h1>接收Session域对象的数据</h1>
<div>
    <!-- 在获取session对象的数据时，直接使用session.get(key)就可以 -->
    <p th:text="'message='+${session.get('msg')}"></p>
    <p th:text="'username='+${session.get('username')}"></p>
    <p th:text="'password='+${session.get('password')}"></p>
</div>
```



#### Application域

> 向application域对象发送数据。application域是最大的域对象

```java
@RequestMapping("app")
public String sendMsgByApp(String username,String password, HttpSession session){
    // 想要通过Application域对象发送数据，就需要先通过session获取application域
    // 在这里application域对象其实就是项目的上下文域对象，即全局对象
    ServletContext servletContext = session.getServletContext();
    servletContext.setAttribute("msg","app msg");
    servletContext.setAttribute("username",username);
    servletContext.setAttribute("password",password);
    return "success";
}
```

**取值**

```html
<h1>接收Application域对象的数据</h1>
<div>
    <!-- 取上下文域对象数据时需使用application.get(key)， -->
    <p th:text="'message='+${application.get('msg')}"></p>
    <p th:text="'username='+${application.get('username')}"></p>
    <p th:text="'password='+${application.get('password')}"></p>
</div>
```



### 视图

> 视图作为SpringMVC的三大模块之一，其种类也是繁杂的，且支持扩展。
>
> 其本身视图只有两个
>
> - 转发视图，在服务器内部进行请求跳转。客户端只发起一次请求，url为第一次请求的地址
> - 重定向视图，客户端发起请求，服务器响应，这次请求包含客户端的另外一个请求，相当于客户端再次发起请求，且重定向之后的url地址为第二次请求的地址

#### 转发视图

> SpringMVC中转发视图默认为InternalResourceView，
>
> 以`forword:`为前缀

#### 重定向视图

> SpringMVC中重定向视图默认为RedirectView
>
> 以`redirect:`为前缀

#### 视图控制器

> 在Controller方法中，有一些请求仅仅用于页面跳转，此时我们可以使用<view-controoler>配置此类请求。
>
> 如默认进入index.html的请求为
>
> ```java
> @RequestMapping(value = "/")
> public String login(){
>     return "index";
> }
> ```
>
> 此时我们可以通过配置文件配置来代替上面这种请求
>
> ```xml
> <!-- 添加view-controller，起作用类似rooter，当访问 / 路径时，会自动跳转进index页面 -->
> <!--path:就是要访问的路径，view-name：就是要访问的视图名称-->
> <mvc:view-controller path="/" view-name="index"/>
> ```
>
> 但是，这会导致一个问题，就是其他使用`@RequestMapping`注解的请求不在起作用。
>
> 想要解决这个问题，我们需要引入另外一个标签。
>
> ```xml
> <!--启用mvc的注解驱动-->
> <mvc:annotation-driven/>
> ```

### RESTful

> **Re**presentational **S**tate **T**ransfer：REST，表现层资源状态迁移

#### 简介

> 资源

#### 实现





### 常见错误

#### 405

> 请求方式不支持











## Spring文件模板

> `applicationContext.xml`
>
> ```xml
><?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
> xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>  xmlns:aop="http://www.springframework.org/schema/aop"
>  xmlns:context="http://www.springframework.org/schema/context"
>  xsi:schemaLocation="http://www.springframework.org/schema/beans
>     http://www.springframework.org/schema/beans/spring-beans.xsd
>     http://www.springframework.org/schema/aop
>     http://www.springframework.org/schema/aop/spring-aop.xsd
>     http://www.springframework.org/schema/context
>     http://www.springframework.org/schema/context/spring-context.xsd">
>    <!--  带有context、aop配置规范的xml  -->
>    </beans>
>    ```
>    
>  `web.xml`
> 
> ```xml
><?xml version="1.0" encoding="UTF-8"?>
> <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
>   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
>                 http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
>    version="4.0">
>    
>    </web-app>
>    ```
>    
> `mybatis-config.xml`
> 
> ```xml
><?xml version="1.0" encoding="UTF-8" ?>
> <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
>  "http://mybatis.org/dtd/mybatis-3-config.dtd">
> <configuration>
> <!-- mybatis-config的模板-->
> </configuration>
>    ```
> 
>  `mapper.xml`
> 
> ```xml
><?xml version="1.0" encoding="UTF-8"?>
> <!DOCTYPE mapper
>  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
>   "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
> <!--mapper 相当于Dao层接口类的实现类,namespace 放的是实现的接口的路径-->
> <mapper namespace="com.marshio.dao.StudentDao">
>    
>    </mapper>
> ```
> 
> `log4j.properties`
> 
> ```properties
># 声明日志的输出级别及输出方式
> log4j.rootLogger=DEBUG,stdout
># MyBatis logging configuration...
> log4j.logger.org.mybatis.example.BlogMapper=TRACE
> # Console output...
> log4j.appender.stdout=org.apache.log4j.ConsoleAppender
> log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
> # 定义日志的打印格式  %t 表示线程名称  %5p 日志级别 %msg日志信息
> log4j.appender.stdout.layout.ConversionPattern=[%t] %5p - %m%n
> ```
> 
> 




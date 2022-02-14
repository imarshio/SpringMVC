# SpringMVC-demo4



> - RESTful风格
>     - 增
>     - 删
>     - 改
>     - 查



## 实现REST的准备工作



### 搭建项目

略

#### 项目结构

![image-20220214163908413](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220214163908.png)

> - Java：存放Java类
>     - bean：存放实体类，使用`@Component`注解
>     - controller：控制器类，使用`@Controller`注解
>     - dao：持久层，使用`@Repository`注解
>     - service：服务层，使用`@Service`注解
> - resource：存放资源、配置文件
> - webapp：存放web相关文件
>     - static：存放静态文件（除html）
>     - WEB-INF：存放web.xml和html等页面

### 配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--设置字符编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <!--注意这里必须是 /* -->
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--配置隐藏的HTTP请求过滤器，此过滤器支持将POST请求转换成PUT和DELETE请求-->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--设置前端控制器Dispatcher Servlet-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>

</web-app>
```



### Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置注解扫描-->
    <context:component-scan base-package="com.marshio.mvc.*"/>

    <!--配置视图控制器，注意此处的 / 代表的是相对路径的-->
    <mvc:view-controller path="/" view-name="index"/>
    <mvc:view-controller path="/toAdd" view-name="addEmployee"/>

    <!--配置thyme leaf视图解析器-->
    <bean id="thymeleafViewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <!--配置模板引擎-->
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <!--配置模板解析器-->
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <property name="prefix" value="/WEB-INF/templates/"/>
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <!--
       处理静态资源，例如html、js、css、jpg
      若只设置该标签，则只能访问静态资源，其他请求则无法访问
      此时必须设置<mvc:annotation-driven/>解决问题
    -->
    <mvc:default-servlet-handler/>

    <!--配置mvc的注解驱动-->
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="defaultCharset" value="UTF-8"/>
                <property name="supportedMediaTypes">
                    <list>
                        <value>test/html</value>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

</beans>
```



### 实体类

```java
package com.marshio.mvc.bean;

/**
 * @author masuo
 * @data 11/2/2022 下午4:32
 * @Description bean
 */

public class Employee {

    private Integer id;
    private String lastName;

    private String email;
    //1 male, 0 female
    private Integer gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Employee(Integer id, String lastName, String email, Integer gender) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public Employee() {
    }
}
```



### 持久层

```java
package com.marshio.mvc.dao;

import com.marshio.mvc.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author masuo
 * @data 11/2/2022 下午4:33
 * @Description dao层即持久层，需要使用Repository来声明持久层
 */

@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> employees = null;

    static{
        employees = new HashMap<>();
		//准备静态数据，模拟读取数据库数据
        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1));
        employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1));
        employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0));
        employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0));
        employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1));
    }

    private static Integer initId = 1006;

    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
    }

    public Collection<Employee> getAll(){
        return employees.values();
    }

    public Employee get(Integer id){
        return employees.get(id);
    }

    public void delete(Integer id){
        employees.remove(id);
    }
}
```



## 功能清单

| 功能         | URL         | 请求方式 |
| ------------ | ----------- | -------- |
| 访问首页     | /           | GET      |
| 查询全部数据 | /employee   | GET      |
| 查询一条数据 | /employee/1 | GET      |
| 访问添加页面 | /toAdd      | GET      |
| 添加         | /add        | POST     |
| 访问更新页面 | /toUpdate   | GET      |
| 更新         | /update     | PUT      |
| 删除         | /employee/1 | DELETE   |



### 功能实现

#### 控制器初始化

- 方式一：构造时初始化

```java
@Controller
public class RestfulController {

    EmployeeDao employeeDao;

    /**
     * 构造函数，在构造时在为EmployeeDao赋值,
     */
    public RestfulController(){
        employeeDao = new EmployeeDao();
    }
}
```

- 方法二：@Autowired注解

```java
@Controller
public class RestfulController {

    //Autowired注解会为employeeDao自动注入，但是不推荐使用此注解
    @Autowired
    EmployeeDao employeeDao;
}
```



#### 访问首页

```xml
<!--因为访问首页只是一个请求，没有业务需求，所以使用view-controller就可以实现-->
<mvc:view-controller path="/" view-name="index"/>
```

#### 查询全部数据

- **请求**

```html
<a th:href="@{/employees}">查询全部数据</a>
```

- **控制器**

```java
/**
 * 查询所有员工
 * @return employees
 */
@RequestMapping(value = "/employees" , method = RequestMethod.GET)
public String employees(HttpServletRequest request){
    //此处我们因为每次都会请求新的数据，所以我们向request域对象中共享数据
    System.out.println("Getting all employees...");
    request.setAttribute("employees",employeeDao.getAll());
    //返回视图名称
    return "employees";
}
```



#### 访问添加页面

- **请求**

```html
<a th:href="@{/toAdd}">add</a>
```

- **spring-mvc.xml**

```java
<!--同访问首页，此处无业务操作，直接返回视图名称即可-->
<mvc:view-controller path="/toAdd" view-name="addEmployee"/>
```



#### 添加实现

- **addEmployee.html**

```html
<form th:action="@{/addEmployee}" method="post">
    <label>
        lastname:<input name="lastName">
    </label><br>
    <label>
        email:<input name="email">
    </label><br>
    <label>
        gender:
        <input type="radio" value="1" name="gender">男
        <input type="radio" value="0" name="gender">女
    </label><br>
    <input type="submit" value="新增">
</form>
```

- **控制器**

```java
/**
 * 新增员工，POST请求
 * @return employees
 */
@RequestMapping(value = "/addEmployee",method = RequestMethod.POST)
public String addEmployee(Employee employee){
    //此处我们使用bean接收实体对象参数
    System.out.println("Adding a employee"+employee.toString());
    //调用持久层方法
    employeeDao.save(employee);
    //重定向到employees视图
    return "redirect:/employees";
}
```

#### 访问更新页面

- **请求**

```html
<a th:href="@{'/toUpdate/'+${employee.id}}">修改</a>
```

- **控制器**

```java
/**
 * 根据id获取employee，然后跳转到更新页面
 * @return employees
 */
@RequestMapping(value = "/toUpdate/{id}",method = RequestMethod.GET)
public String toUpdate(@PathVariable("id") Integer id, Model model){
    Employee employee = employeeDao.get(id);
    //因为需要回显数据信息，此处我们选择model向request域对象共享数据
    model.addAttribute("employee",employee);
    return "updateEmployee";
}
```

#### 更新实现

- **updateEmployee.html**

```html
<form th:action="@{/updateEmployee}" th:each="employee:${employee}" method="post">
    <!--设置隐藏属性，"id"，因为要根据id更新，且id不可更改-->
    <label><input type="hidden" name="id" th:value="${id}"></label>
    <!--设置隐藏属性，"_method"因为我们需要根据此属性的值将POST请求转换成PUT请求-->
    <label><input type="hidden" name="_method" value="PUT"></label>
    <label>
        lastname:<input name="lastName" th:value="${employee.lastName}">
    </label><br>
    <label>
        email:<input name="email" th:value="*{employee.email}">
    </label><br>
    <label>
        gender:
        <!--th:field 可以设置选中的默认值-->
        <input type="radio" value="1" name="gender" th:field="*{employee.gender}">男
        <input type="radio" value="0" name="gender" th:field="*{employee.gender}">女
    </label><br>
    <input type="submit" value="修改">
</form>
```

- **控制器**

```java
/**
 * 更新数据，并回显
 * @return employees
 */
@RequestMapping(value = "/updateEmployee",method = RequestMethod.PUT)
public String updateEmployee(Employee employee){
    System.out.println("Updating an employee:"+employee.toString());
    employeeDao.save(employee);
    return "redirect:/employees";
}
```



#### 删除实现

> 删除的实现是比较难得。涉及多个步骤。
>
> - 以超链接的形式发起删除请求
> - 写隐藏form表单，模拟删除请求

- 创建处理删除请求的form**表单**

```html
<form id="del-form" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
</form>
```

- 超链接删除，取消其默认行为

    - 引入vue.js

    ```html
    <script type="application/javascript" th:src="@{/static/js/vue.js}"></script>
    ```

    

    - 发起请求

    ```html
    <a id="delEmployee" @click="delEmployee" th:href="@{'/del/'+${employee.id}}">删除</a>
    ```

    

    - 处理点击事件

    ```html
    <script type="application/javascript">
        new Vue({
            //id选择器
            el: "#delEmployee",
            methods: {
                //event 表示当前事件
                delEmployee:function(event) {
                    //通过id获取表单标签
                    var delForm = document.getElementById("del-form");
                    //将超链接的地址赋值给表单的action属性
                    delForm.action = event.target.href;
                    //表单提交
                    delForm.submit();
                    //取消超链接的默认行为 --- 即取消跳转
                    event.preventDefault();
                }
            }
        });
    </script>
    ```

- **控制器**

```java
/**
 * 更新数据，并回显
 * @return employees
 */
@RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
public String delEmployee(@PathVariable("id")Integer id){
    System.out.println("Deleting an employee:"+id);
    employeeDao.delete(id);
    return "redirect:/employees";
}
```



完整代码，请看 https://github.com/Masuo777/SpringMVC/tree/main/SpringMVC-demo4




# SpringMVC-demo3

> - 常用注解
> - 前端数据传向后端，
> - 后端数据传向前端



### 常用注解

- @Controller
- @RequestMapping
- @RequestBody
- @RestController



### 前-》后

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

### 后-》前

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


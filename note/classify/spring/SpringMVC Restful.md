# [SpringMVC RESTful](https://tech.imdada.cn/2015/12/23/springmvc-restful-optimize/)

## [Spring mve Configuration](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/EnableWebMvc.html)
  1. @EnableWebMvc+extends WebMvcConfigurationAdapter<br/>
    在扩展的类中重写父类的方法即可，这种方式会屏蔽springboot的@EnableAutoConfiguration中的设置
  2. extends WebMvcConfigurationSupport(DelegatingWebMvcConfiguration)<br/>
    在扩展的类中重写父类的方法即可，这种方式会屏蔽springboot的@EnableAutoConfiguration中的设置
  3. extends WebMvcConfigurationAdapter<br/>
    在扩展的类中重写父类的方法即可，这种方式依旧使用springboot的@EnableAutoConfiguration中的设置

## @RequestMapping
    RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址
    作为父路径。
  - RequestMapping注解属性
  
  |   name   |   explain   |
  |------|------|
  |   value(path)   |   指定请求的实际地址，指定的地址可以是URI Template 模式   |
  |   method   |   指定请求的method类型， GET、POST、PUT、DELETE等   |
  |   consumes   |   指定处理请求的提交内容类型（Content-Type），例如application/json, text/html   |
  |   produces   |    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回   |
  |   params   |    指定request中必须包含某些参数值是，才让该方法处理  |
  |   headers   |   指定request中必须包含某些指定的header值，才能让该方法处理请求   |


## SpringMVC 请求路径匹配过程(AbstractHandlerMethodMapping.lookupHandlerMethod)
![](../../resources/AbstractHandlerMethodMapping.png)







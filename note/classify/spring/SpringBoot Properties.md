# Spring Boot Properties Load
SpringBoot中免除了大部分手动配置，但是对于一些特定的情况，还是需要我们进行手动配置的，SpringBoot为我们提供了application.properties配置文件，让我们可以进行自定义配置，来对默
认的配置进行修改，以适应具体的生产情况，当然还包括一些第三方的配置。几乎所有配置都可以写到application.peroperties文件中，这个文件会被SpringBoot自动加载，免去了我们手动加载
的烦恼。但实际上，很多时候我们却会自定义配置文件，这些文件就需要我们进行手动加载，SpringBoot是不会自动识别这些文件的，下面就来仔细看看这些方面的内容。

  1. 配置文件的格式
　　SpringBoot可以识别两种格式的配置文件，分别是yml文件与properties文件，我们可以将application.properties文件换成application.yml，这两个文件都可以被SpringBoot自动识别并加载，
    如果是自定义的配置文件，就最好还是使用properties格式的文件，因为SpringBoot中暂时还并未提供手动加载yml格式文件的功能（这里指注解方式）。
　　application.properties
    配置文件欲被SpringBoot自动加载，需要放置到指定的位置：src/main/resource目录下，一般自定义的配置文件也位于此目录之下。

  2. 配置文件的加载
        1. 公共配置文件：application.properties
        application.properties配置文件是在SpringBoot项目启动的时候被自动加载的，其内部的相关设置会自动覆盖SpringBoot默认的对应设置项，所以的配置项均会保存到Spring容器之中。
        ```text
        1 donghao.name=唯一浩哥
        2 donghao.sex=男
        3 donghao.age=80
        ```

        2. 自定义配置文件：donghao.properties
        自定义的xxx.properties配置文件是不会被SpringBoot自动加载的，需要手动去进行加载，这里的手动加载一般指的是注解的方式加载，这里就涉及到我们今天的重点之一：加载自定义属性
        文件的注解：@PropertySource("classpath:xxx.properties")，这个注解专门用来加载指定位置的properties文件，Spring暂未提供加载指定位置yml文件的注解，所以才有之前的说法。
        ```text
        1 donghao1.name=动画
        2 donghao1.sex=女
        3 donghao1.age=22
        ```


  3. 配置项的使用
　　配置项的使用其实很简单，只要是加载到Spring容器中的配置项都可以直接使用@Value("${key}")的方式来引用，一般将其配置在字段顶部，表示将配置项的值赋值给该字段。
　　当然更多的情况是将这些配置项与一个JavaBean绑定起来使用，这样绑定一次，我们就可以随时使用。这里涉及到两种情况，一种是将application.properties中的配置与JavaBean绑定，一种是
    将自定义配置文件中的配置与Javabean绑定。

　　第一种：applicaiton.properties属性绑定JavaBean
　　这种情况相对简单（因为application.properties文件会被自动加载，也就是说配置项会被自动加载到内存，到Spring容器之中，省去了手动加载的配置），然后我们在要与属性绑定的JavaBean
    的类定义顶部加@Component注解和@ConfigurationProperties(prefix="key")注解，前者的目的是为了这个JavaBean可以被SpringBoot项目启动时候被扫描到并加载到Spring容器之中，重点是后
    者，这个注解一般不是单独使用的，他一般与后面要说的@EnableConfigurationProperties(JavaBean.class)配合使用，但是二者并非使用在同一位置，@ConfigurationProperties(prefix="key")
    注解加注在JavaBean类定义之上，按字面可以理解为属性配置注解，更直接点的说法就是属性绑定注解，官方解释是：如果想要绑定或者验证一些来源自.properties文件中的额外属性时，你可以
    在一个标注的@Configuration的类的注有@Bean注解的方法或者一个类之上加注这个注解。我们完全可以将其理解为绑定专用注解。它的作用就是将指定的前缀的配置项的值与JavaBean的字段绑定，
    这里要注意，为了绑定的成功，一般将字段的名称与配置项键的最后一个键名相同，这样整个键在去掉前缀的情况下就和字段名称一致，以此来进行绑定。

　　第二种：自定义配置的属性绑定JavaBean
　　这种情况与之前的基本相同，只是不能自动加载，需要手动加载，在JavaBean之上加上之前介绍的@PropertySource注解进行配置文件加载。还有一点就是将@Component改为@Configuration。
　　这里要注意一点：当我们在某个类中要使用这个JavaBean时，需要在这个类中指定这个JavaBean的类型，这个指定也要使用注解来制定，正是之前介绍的@EnableConfigurationProperties注解，
    这个注解与@ConfigurationProperties注解配套使用。官方给出的解释：这个注解是对@ConfigurationProperties的有效支持。标注有@ConfigurationProperties注解的Beans可以被使用标准的
    方式注册（使用@Bean注解），或者，为了方便起见，直接用使用@EnableConfigurationProperties指定注册。意思是这个注解提供了一种方便直接的注册Bean的方式。
   
```java
     @Configuration
     @PropertySource("classpath:donghao.properties")
     @ConfigurationProperties(prefix="donghao1")
     public class Donghao {
         private String name;
         private String sex;
         private String age;
         public String getName() {
             return name;
         }
         public void setName(String name) {
             this.name = name;
         }
         public String getSex() {
             return sex;
         }
         public void setSex(String sex) {
             this.sex = sex;
         }
         public String getAge() {
             return age;
         }
         public void setAge(String age) {
             this.age = age;
         }
     }

 @RestController
 @RequestMapping("/donghao")
 @EnableConfigurationProperties(Donghao.class)
 public class DonghaoController {
     
     @Autowired
     Donghao donghao;
     
     @Value("${donghao.name}")
     private String name;
     
     @Value("${donghao.sex}")
     private String sex;
     
     @Value("${donghao.age}")
     private String age;

     @RequestMapping("/hello")
     public String hello(){
         return "我的名字叫"+name+",我是"+sex+"生,今年"+age+"岁了!";
     }
     
     @RequestMapping("/ss")
     public String ss(){
         return donghao.getName()+donghao.getSex()+donghao.getAge();
     }
 }
```
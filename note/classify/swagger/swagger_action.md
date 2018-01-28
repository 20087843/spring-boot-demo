# swagger action

## dependency 
    ```xml
    <dependencies>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.2.2</version>
        </dependency>
        
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.2.2</version>
        </dependency>
    </dependencies>
    ```

## configuration(spring boot)
```java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pri.smilly"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo rest server")
                .description("this is a demo project of rest api")
                .termsOfServiceUrl("https://github.com/20087843/demo.springboot.composite")
                .contact("smilly")
                .version("2.0")
                .build();
    }

}
```


## annotation
| annotation | description |
|--------|--------|
|    @Api    |    修饰整个类，描述Controller的作用    |
|    @ApiOperation    |    描述一个类的一个方法，或者说一个接口    |
|    @ApiParam    |    单个参数描述    |
|    @ApiModel    |    用对象来接收参数    |
|    @ApiProperty    |    用对象接收参数时，描述对象的一个字段    |
|    @ApiResponse    |    HTTP响应其中1个描述    |
|    @ApiResponses    |    HTTP响应整体描述    |
|    @ApiIgnore    |    使用该注解忽略这个API    |
|    @ApiError    |    发生错误返回的信息    |
|    @ApiImplicitParam    |    一个请求参数    |
|    @ApiImplicitParams    |    多个请求参数    |


## example
```java
@RestController
@RequestMapping(value="/users")
public class UserController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value="get users", notes="get all users")
    @RequestMapping(value={""}, method=RequestMethod.GET)
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }

    @ApiOperation(value="save user", notes="save a user")
    @ApiImplicitParam(name = "user", value = "user data", required = true, dataType = "User")
    @RequestMapping(value="", method=RequestMethod.POST)
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value="get a user", notes="get a user by id")
    @ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value="update user", notes="update user info by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "user data", required = true, dataType = "User")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value="delete user", notes="delete a user by id")
    @ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
}
```
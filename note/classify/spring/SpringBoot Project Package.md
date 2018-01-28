#  SpringBoot应用与原理之项目打包方案

## 环境规划
| 环境名称 | 环境说明 |
|--------|--------|
|    dev    |    开发环境    |
|    fat    |    测试环境    |
|    uat    |    集成环境    |
|    pro    |    生产环境    |

## SpringBoot应用配置
在明确环境规划之后准备5个配置文件，分别是:
  - application.properties
  通常用于配置通用的属性，也就是不同环境下的配置项是相同的，profiles.active是pom.xml中配置profile的变量,用于替换激活的环境
    ```text
    spring.profiles.active=@profiles.active@
    ```
  - application-dev.properties
    ```text
      server.port=8099
    ```
  - application-fat.properties
    ```text
        server.port=8099
    ```
  - application-uat.properties
    ```text
        server.port=8099
    ```
  - application-pro.properties
    ```text
        server.port=8099
    ```

 ## maven打包设置
 
 ```xml
 <project>
    <profiles>
         <!-- 定义四个不同环境,分别是开发环境dev,测试环境fat,集成环境uat,生产环境pro-->
         <profile>
             <id>dev</id>
             <properties>
                 <profiles.active>dev</profiles.active>
             </properties>
             <!-- 默认激活开发环境-->
             <activation>
                 <activeByDefault>true</activeByDefault>
             </activation>
         </profile>
 
         <profile>
             <id>fat</id>
             <properties>
                 <profiles.active>fat</profiles.active>
             </properties>
         </profile>
 
         <profile>
             <id>uat</id>
             <properties>
                 <profiles.active>uat</profiles.active>
             </properties>
         </profile>
 
         <profile>
             <id>pro</id>
             <properties>
                 <profiles.active>pro</profiles.active>
             </properties>
         </profile>
    </profiles>
    
    <build>
        <resources>
            <!-- 指定打包时需要过滤的文件-->
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <excludes>
                    <exclude>application-dev.properties</exclude>
                    <exclude>application-fat.properties</exclude>
                    <exclude>application-uat.properties</exclude>
                    <exclude>application-pro.properties</exclude>
                </excludes>
            </resource>

            <!-- 指定打包时需要包含的配置文件-->
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <!--${profiles.active}会根据指定的profile动态替换-->
                    <include>application-${profiles.active}.properties</include>
                    <include>application.properties</include>
                </includes>
            </resource>
        </resources>
    </build>
 </project>
 ```
 
 ## 用maven的打包
 ```text
    mvn clean package -Dmaven.test.skip=true -P fat
 ```
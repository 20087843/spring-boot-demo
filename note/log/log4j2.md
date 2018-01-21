# log4j2 configuration

### 配置文件节点解析　　　　
  1. 根节点Configuration有两个属性:status和monitorinterval,有两个子节点:Appenders和Loggers(表明可以定义多个Appender和Logger).
     - status用来指定log4j本身的打印日志的级别.
     - monitorinterval用于指定log4j自动重新配置的监测间隔时间，单位是s,最小是5s.
  2. Appenders节点，常见的有三种子节点:Console、RollingFile、File.
     - Console节点用来定义输出到控制台的Appender.
     - name:指定Appender的名字.
     - target:SYSTEM_OUT 或 SYSTEM_ERR,一般只设置默认:SYSTEM_OUT.
     - PatternLayout:输出格式，不设置默认为:%m%n.
  3. File节点用来定义输出到指定位置的文件的Appender.
      - name:指定Appender的名字.
      - fileName:指定输出日志的目的文件带全路径的文件名.
      - PatternLayout:输出格式，不设置默认为:%m%n.
  4. RollingFile节点用来定义超过指定大小自动删除旧的创建新的的Appender.
      - name:指定Appender的名字.
      - fileName:指定输出日志的目的文件带全路径的文件名.
      - PatternLayout:输出格式，不设置默认为:%m%n.
      - filePattern:指定新建日志文件的名称格式.
      - Policies:指定滚动日志的策略，就是什么时候进行新建日志文件输出日志.
          - TimeBasedTriggeringPolicy:Policies子节点，基于时间的滚动策略，interval属性用来指定多久滚动一次，默认是1 hour。modulate=true用来调整时间：比如现在是早上3am，interval是4，那么第一次滚动是在4am，接着是8am，12am...而不是7am.
          - SizeBasedTriggeringPolicy:Policies子节点，基于指定文件大小的滚动策略，size属性用来定义每个日志文件的大小.
          - DefaultRolloverStrategy:用来指定同一个文件夹下最多有几个日志文件时开始删除最旧的，创建新的(通过max属性)。
  5. Loggers节点，常见的有两种:Root和Logger.
      - Root节点用来指定项目的根日志，如果没有单独指定Logger，那么就会默认使用该Root日志输出
         - level:日志输出级别，共有8个级别，按照从低到高为：All < Trace < Debug < Info < Warn < Error < Fatal < OFF.
         - AppenderRef：Root的子节点，用来指定该日志输出到哪个Appender.
      - Logger节点用来单独指定日志的形式，比如要为指定包下的class指定不同的日志级别等。
         - level:日志输出级别，共有8个级别，按照从低到高为：All < Trace < Debug < Info < Warn < Error < Fatal < OFF.
         - name:用来指定该Logger所适用的类或者类所在的包全路径,继承自Root节点.
         - AppenderRef：Logger的子节点，用来指定该日志输出到哪个Appender,如果没有指定，就会默认继承自Root.如果指定了，那么会在指定的这个Appender和Root的Appender中都会输出，此时我们可以设置Logger的additivity="false"只在自定义的Appender中进行输出。
  6. 关于日志level(共有8个级别，按照从低到高为：All < Trace < Debug < Info < Warn < Error < Fatal < OFF)
        - All:最低等级的，用于打开所有日志记录.
        - Trace:是追踪，就是程序推进以下，你就可以写个trace输出，所以trace应该会特别多，不过没关系，我们可以设置最低日志级别不让他输出.
        - Debug:指出细粒度信息事件对调试应用程序是非常有帮助的.
        - Info:消息在粗粒度级别上突出强调应用程序的运行过程.
        - Warn:输出警告及warn以下级别的日志.
        - Error:输出错误信息日志.
        - Fatal:输出每个严重的错误事件将会导致应用程序的退出的日志.
        - OFF:最高等级的，用于关闭所有日志记录.
        
        
### pattern 说明
  - c  category的名称，可使用｛n}限制输出的精度。例如：logger名为"a.b.c"，%c{2}将输出"b.c"。 
  - C  产生log事件的java完全限定类名。可使用｛n}限制输出的精度。例如：“org.apache.xyz.SomeClass”,%C{2}将输出“SomeClass”。 
  - d  时间和日期的输出格式，例如：%d{yyyy MM dd HH:mm:ss,SS}，可不带后面的日期格式字符。 
  - F  产生log事件的java源文件名，带“.java”后缀及包名称。 
  - l  log发生位置的详细描述，包括方法名、文件名及行号。 
  - L  log发生在源文件中的位置。 
  - m  log事件的消息内容。 
  - M  log发生时所在的方法名称。 
  - n  根据所运行的平台输出相应的行分隔字符。 
  - p  log事件的级别。 
  - r  自程序运行至log事件产生所经过的时间。 
  - t  产生log的线程名称。 
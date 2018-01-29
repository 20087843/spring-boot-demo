# Quartz

## Introduction
![](http://blog.itpub.net/attachment/201508/6/11627468_1438829844Zbz8.png)

|   name   |   desc   |
|------|------|
|   scheduler   |   任务调度器   |
|   trigger   |   触发器，用于定义任务调度时间规则   |
|   job   |   任务，即被调度的任务   |
|   misfire   |   错过的，指本来应该被执行但实际没有被执行的任务调度  |

## Description
  - Jobs与JobDetails<br/>
    JobDetail就是Job的一个具体实例，Scheduler执行Job时，在调用execute()方法前会先实例化Job；
  一旦执行结束，该实例就会被丢弃，然后被垃圾回收。需要注意的是Job必须有一个无参的构造器；另
  外在Job类中定义数据属性是没有意义的，因为这些属性值并不会在执行期间保存。

  - JobDataMap<br/>
    Job中无法定义属性来传递数据，Job 需要的数据通过 JobDataMap 传递，它是JobDetail的一个属性。
    
## quartz configuration
  1. Quartz主要配置
  
|属性名称|	是否必选|	类型|	默认值|	说明|
|------|------|------|------|------|
|org.quartz.scheduler.instanceName|	否|	String|	QuartzScheduler|	Schedule调度器的实体名字 |
|org.quartz.scheduler.instanceId|	否|	String|	NON_CLUSTERED|	Schedule调度器的实体的Id,必须唯一。<br/>1. 当你想生成intanceId的时候可以设置为AUTO <br/>2. 当你想从系统属性org.quartz.scheduler.instanceId取值时可以设置为SYS_PROP|
|org.quartz.scheduler.instanceIdGenerator.class|	否|	String(类名)|	org.quartz.simpl.SimpleInstanceIdGenerator|	生成Schudule实体Id的类，只有在属性org.quartz.scheduler.instanceId设置为AUTO时使用，默认的实现org.quartz.scheduler.SimpleInstanceGenerator是基于主机名称和时间戳生成。其他的实现查看具体的文档|
|org.quartz.scheduler.threadName|	否|	String|	instanceName + ‘_QuartzSchedulerThread’|	Scheduler线程的名称|
|org.quartz.scheduler.makeSchedulerThreadDaemon|	否|	boolean|	false|	指定Scheduler是否以守护线程(服务)运行|
|org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer|	否|	boolean|	false|	|
|org.quartz.scheduler.idleWaitTime|	否|	long|	30000|	当调度程序空闲时，在重新查询可用触发器之前，调度程序将等待毫秒的时间数。不建议少于5000ms，而少于1000是不合法的参数|
|org.quartz.scheduler.doFailureRetryInterval|	否|	long|	15000|	使用JobStore(比如连接数据库)时Schueduler检测到失去数据库连接后重新尝试连接的毫秒数|
|org.quartz.scheduler.classLoadHelper.class|	否|	String(类名)|	org.quartz.simpl.CascadingClassLoaderHelper| |
|org.quartz.scheduler.jobFactory.class|	否|	String(类名)|	org.quartz.simpl.PropertySettingJobFctory|	给Scheduler Context、Job、Trigger的JobDataMaps设置属性值的方式|
|org.quartz.contenxt.key.SOME_KEY|	否|	String|	None|	键值对，保存在Scheduler Context中，比如有这样的配置org.quartz.shceduler.key.MyKey=MyValue，则在Scheduler Context中赋值方式为scheduler.getContext().put(“MyKey”, “MyValue”)|
|org.quartz.scheduler.userTransactionURL|	否|	String(url)|	java:comp/UserTransaction|	事务管理JNDI URL地址。只有当Quartz使用JobStoreCMT和org.quartz.scheduler.wrapJobExecutionInUserTransaction 设置为true时使用|
|org.quartz.scheduler.wrapJobExecutionInUserTransaction|	否|	boolean|	false|	只有当你在执行一个Job时想使用UserTransaction时设置为true，参考@ExecuteInJTATransaction 注解|
|org.quartz.scheduler.skipUpdateCheck|	否|	boolean|	false|	是否跳过版本检测。可以设置系统参数org.terracotta.quartz.skipUpdateCheck=true或者在JAVA命令行使用-D选项。在正式库运行时应设置为true。|
|org.quartz.scheduler.batchTriggerAcquisitionMaxCount|	否|	int|	1|	在同一时间运行Scheduler获取trigger的数量。如果设置的数量>1，并且使用JDBC JobStore，则属性org.quartz.jobStore.acquireTriggersWithinLock应设置为true，可以避破坏数据。|
|org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow|	否|	long|	0|	运行Scheduler在获取和触发tigger的提前的时间。|

  2. 线程池配置
   - 2.1 主要配置
    
        |属性名称|	是否必选|	类型|	默认值|	说明|
        |------|------|------|------|------|
        |org.quartz.threadPool.class|	是|	String(类名)|	null|	Scheduler使用的线程池名称，实现了ThreadPool接口，参考org.quartz.simpl.SimpleThreadPool|
        |org.quartz.threadPool.threadCount|	是|	int|	-1|	线程池里面的线程的数据，取值在1-100|
        |org.quartz.threadPool.threadPriority|	否|	int|	Thread.NORM_PRIORITY (5)|	线程的优先级,取值在Thread.MIN_PRIORITY(1)到Threa.MAX_PRIORITY(10)|

   - 2.2 线程池的简单配置
    
        |属性名称|	是否必选|	类型|	默认值|	说明|
        |------|------|------|------|------|
        |org.quartz.threadPool.makeThreadsDaemons|	否|	boolean|	fale|	指定在线程池里面创建的线程是否是守护线程|
        |org.quartz.threadPool.threadsInheritGroupOfInitializingThread|	否|	boolean|	true|
        |org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread|	否|	boolean|	false|
        |org.quartz.threadPool.threadNamePrefix|	否|	String	[Scheduler Name]|_Workder	|指定线程池里面线程的名字的前|

  3. Listener配置
  
    org.quartz.triggerListener.NAME = package.className
    org.quartz.triggerListener.NAME.propName = propValue
    org.quartz.jobListener.NAME = package.className
    org.quartz.jobListener.NAME.propName = propValue

  4. JobStore配置
   - 4.1 RAMJobStore(在内存中存储信息的，程序一旦结束便丢失了相关的信息)
 
        |属性名称|	是否必选|	类型|	默认值|	说明|
        |------|------|------|------|------|
        |org.quartz.jobStore.class|	否|	String(类名)|	org.quartz.simpl.RAMJobStore|	指定使用的JobStore|
        |org.quartz.jobStore.misfireThreshold|	否|	int|	60000|	触发器失败后下次触发的时间间隔|

   - 4.2 JDBCJobStore和JobStoreTX(JDBCJobStore和JobStoreTX都使用关系数据库来存储Schedule相关的信息)

        |属性名称|	是否必选|	类型|	默认值|	说明|
        |------|------|------|------|------|
        |org.quartz.jobStore.class|	否|	String(类名)|	org.quartz.simpl.jdbcjobstore.JobStoreTX|	使用JobStoreTX|
        |org.quartz.jobStore.driverDelegateClass|	是|	String(类名)|	null|	使用的数据库驱动，具体的驱动列表详情如下|
        |org.quartz.jobStore.dataSource|	是|	String|	null|	使用的数据源名称，具体参照数据源配置|
        |org.quartz.jobStore.tablePrefix|	否|	String|	QRTZ_|	表的前缀|
        |org.quartz.jobStore.userProperties|	否|	boolean|	false|	标示在JobDataMaps的数据全部是String|
        |org.quartz.jobStore.misfireThreshold|	否|	int|	60000|	触发器触发失败后再次触犯的时间间隔|
        |org.quartz.jobStore.isClustered|	否|	boolean|	false|	如果有多个调度器实体的话则必须设置为true|
        |org.quartz.jobStore.clusterCheckinInterval|	否|	long|	15000|	检查集群下的其他调度器实体的事件间隔|
        |org.quartz.jobStore.maxMisfiresToHandleAtATime|	否|	int|	20	|
        |org.quartz.jobStore.dontSetAutoCommintFalse|	否|	boolean|	false	|
        |org.quartz.jobStore.selectWithLockSQL|	否|	String|	select * from {0}locks where sched_name = {1} and lock_name = ? for update	|
        |org.quartz.jobStore.txlsolationLevelSerializable|	否|	boolean|	false|	
        |org.quartz.jobStore.acquireTriggersWithinLocal|	否|	boolean|	false|	
        |org.quartz.jobStore.lockHandler.class|	否|	String|	null|	
        |org.quartz.jobStore.driverDelegateInitString|	否|	String|	null|

     - 4.2.1 org.quartz.jobStore.driverDelegateClass 数据库驱动列表

        |驱动名称|	说明|
        |------|------|
        |org.quartz.impl.jdbcstore.StdJDBCDelegate| 适用于完全兼容JDBC的驱动|
        |org.quartz.impl.jdbcstore.MSSQLDelegate| 适用于Miscrosoft SQL Server和Sybase数据库|
        |org.quartz.impl.jdbcjobstore.PostgreSQLDelegate||
        |org.quartz.impl.jdbcjobstore.WebLogicDelegate||
        |org.quartz.impl.jdbcjobstore.oracle.OracleDelegate||
        |org.quartz.impl.jdbcjobstore.oracle.WebLogicOracleDelegate||
        |org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate||
        |org.quartz.impl.jdbcjobstore.CloudscapeDelegate||
        |org.quartz.impl.jdbcjobstore.DB2v6Delegate||
        |org.quartz.impl.jdbcjobstore.DB2v7Delegate||
        |org.quartz.impl.jdbcjobstore.DB2v8Delegate||
        |org.quartz.impl.jdbcjobstore.HSQLDBDelegate||
        |org.quartz.impl.jdbcjobstore.PointbaseDelegate||
        |org.quartz.impl.jdbcjobstore.SybaseDelegate||

     - 4.2.2  表说明
     
        | 表名 | 说明 |
        |------|------|
     |RTZ_CALENDARS| 以 Blob 类型存储 Quartz 的 Calendar 信息   |
     |QRTZ_CRON_TRIGGERS| 存储 Cron Trigger，包括Cron表达式和时区信息 |  
     |QRTZ_FIRED_TRIGGERS |存储与已触发的 Trigger 相关的状态信息，以及相联 Job的执行信息QRTZ_PAUSED_TRIGGER_GRPS 存储已暂停的 Trigger组的信息   |
     |QRTZ_SCHEDULER_STATE| 存储少量的有关 Scheduler 的状态信息，和别的Scheduler实例(假如是用于一个集群中)  | 
     |QRTZ_LOCKS| 存储程序的悲观锁的信息(假如使用了悲观锁)   |
     |QRTZ_JOB_DETAILS| 存储每一个已配置的 Job 的详细信息   |
     |QRTZ_JOB_LISTENERS| 存储有关已配置的 JobListener的信息   |
     |QRTZ_SIMPLE_TRIGGERS| 存储简单的Trigger，包括重复次数，间隔，以及已触的次数   |
     |QRTZ_BLOG_TRIGGERS| Trigger 作为 Blob 类型存储(用于 Quartz 用户用JDBC创建他们自己定制的 Trigger 类型，JobStore并不知道如何存储实例的时候)|   
     |QRTZ_TRIGGER_LISTENERS| 存储已配置的 TriggerListener的信息   |
     |QRTZ_TRIGGERS| 存储已配置的 Trigger 的信息   |
## quartz in spring
  - ways
    1. 利用JobDetailBean包装QuartzJobBean子类（即Job类）的实例。
    2. 利用MethodInvokingJobDetailFactoryBean工厂Bean包装普通的Java对象（即Job类）。
    
  - autowire in job
    1. 配置 JobFactory
    ```java
    @Configuration
    public class QuartzConfiguration {
    
        @Autowired
        private SpringAdaptableJobFactory jobFactory;
    
        @Bean
        public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
            factory.setJobFactory(jobFactory);
            factory.setQuartzProperties(quartzProperties());
            return factory;
        }
    
        @Bean
        public Properties quartzProperties() throws IOException {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        }
    
        @Bean
        public QuartzInitializerListener executorListener() {
            return new QuartzInitializerListener();
        }
    
        @Bean
        public Scheduler scheduler() throws IOException {
            return schedulerFactoryBean().getScheduler();
        }
    
    }

    @Component
    public class SpringAdaptableJobFactory extends SpringBeanJobFactory {
    
        @Autowired
        private AutowireCapableBeanFactory autowireCapableBeanFactory;
    
        @Override
        public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
            Job job = super.newJob(bundle, scheduler);
            autowireCapableBeanFactory.autowireBean(job);
            return job;
        }
    }
    ```
    2. 说明
      JobFactory 中 Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) 通过反射方式调用 Job 的空构造创建了一个 Job 实例，
    交给了 Quartz 的 worker 线程去执行，自定义 SpringAdaptableJobFactory 在实例化 Job 后，将 Spring 对象注入到 Job 中。


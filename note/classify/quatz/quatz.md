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

